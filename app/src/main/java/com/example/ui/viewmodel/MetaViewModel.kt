package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.SavedPatchEntity
import com.example.data.local.ThemeSetting
import com.example.data.local.UserPreferencesRepository
import com.example.data.model.GameGenre
import com.example.data.model.HeroRoster
import com.example.data.model.PatchAnalysisReport
import com.example.data.model.PatchTimelineEngine
import com.example.data.model.PlayerTimelineCatchup
import com.example.data.model.PresetPatchNote
import com.example.data.model.PresetPatches
import com.example.data.remote.MetaAnalystEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MetaUiState(
    val selectedGenre: GameGenre = GameGenre.SHOOTER,
    val genreFilter: GameGenre? = null, // null means "All Genres"
    val homeSearchQuery: String = "",
    val gameTitle: String = "Valorant",
    val patchVersion: String = "Patch 8.11",
    val patchNotesText: String = "",
    val isAnalyzing: Boolean = false,
    val currentReport: PatchAnalysisReport? = null,
    val isSaved: Boolean = false,
    val selectedTab: Int = 0, // 0: Studio / Hub, 1: Active Analysis, 2: Saved Vault
    val analysisSectionTab: Int = 0, // 0: Heroes, 1: Arsenal, 2: Macro, 3: Full Brief
    val searchQuery: String = "",
    val toastMessage: String? = null,
    // Returning player timeline catch-up fields
    val catchupGameTitle: String = "Valorant",
    val catchupYear: Int = 2024,
    val catchupMonth: Int? = null, // null = Any month / full year
    val catchupReport: PlayerTimelineCatchup? = null,
    // Personalization: Favorite game & heroes & theme
    val favoriteGame: String = "Valorant",
    val favoriteHeroes: Set<String> = setOf("Iso", "Neon"),
    val themeSetting: ThemeSetting = ThemeSetting.SYSTEM,
    val hasCompletedOnboarding: Boolean = false,
    val showOnboardingDialog: Boolean = false,
    val showPreferencesDialog: Boolean = false
)

class MetaViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).savedPatchDao()
    private val analystEngine = MetaAnalystEngine()
    private val prefsRepo = UserPreferencesRepository(application)

    private val _uiState = MutableStateFlow(MetaUiState())
    val uiState: StateFlow<MetaUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    val savedReports: StateFlow<List<PatchAnalysisReport>> = combine(
        dao.getAllSavedPatches(),
        _searchQuery
    ) { entities, query ->
        val list = entities.map { it.toDomain() }
        if (query.isBlank()) {
            list
        } else {
            list.filter {
                it.gameTitle.contains(query, ignoreCase = true) ||
                        it.patchVersion.contains(query, ignoreCase = true) ||
                        it.summaryHeadline.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Load user personalization preferences
        val userPrefs = prefsRepo.preferences.value
        val favGame = userPrefs.favoriteGame ?: "Valorant"
        val favHeroes = if (userPrefs.favoriteHeroes.isNotEmpty()) {
            userPrefs.favoriteHeroes
        } else {
            HeroRoster.getHeroesForGame(favGame).take(2).map { it.name }.toSet()
        }

        _uiState.value = _uiState.value.copy(
            favoriteGame = favGame,
            favoriteHeroes = favHeroes,
            themeSetting = userPrefs.themeSetting,
            hasCompletedOnboarding = userPrefs.hasCompletedOnboarding,
            showOnboardingDialog = !userPrefs.hasCompletedOnboarding
        )

        // Find preset for favorite game and load it automatically
        val matchingPreset = PresetPatches.allPresets.find {
            it.gameTitle.equals(favGame, ignoreCase = true)
        } ?: PresetPatches.allPresets.first()

        loadPreset(matchingPreset)
        updateCatchupReport(favGame, 2024, null)

        // Automatically run initial analysis for the user's favorite game so they dive in right away!
        analyzePatch()
    }

    fun completeOnboarding(game: String, heroes: Set<String>) {
        prefsRepo.completeOnboarding(game, heroes)
        val matchingPreset = PresetPatches.allPresets.find {
            it.gameTitle.equals(game, ignoreCase = true)
        } ?: PresetPatches.allPresets.first()

        _uiState.value = _uiState.value.copy(
            favoriteGame = game,
            favoriteHeroes = heroes,
            hasCompletedOnboarding = true,
            showOnboardingDialog = false,
            catchupGameTitle = game,
            selectedGenre = matchingPreset.genre,
            gameTitle = matchingPreset.gameTitle,
            patchVersion = matchingPreset.patchVersion,
            patchNotesText = matchingPreset.rawPatchNotes,
            toastMessage = "Locked in $game profile with ${heroes.size} signature heroes!"
        )
        updateCatchupReport(game, _uiState.value.catchupYear, _uiState.value.catchupMonth)
        analyzePatch()
    }

    fun setFavoriteGame(game: String) {
        prefsRepo.setFavoriteGame(game)
        val roster = HeroRoster.getHeroesForGame(game)
        val currentFavs = _uiState.value.favoriteHeroes
        val relevantFavs = currentFavs.filter { name -> roster.any { it.name == name } }.toSet()
        val newFavs = if (relevantFavs.isNotEmpty()) relevantFavs else roster.take(2).map { it.name }.toSet()
        prefsRepo.setFavoriteHeroes(newFavs)

        val matchingPreset = PresetPatches.allPresets.find {
            it.gameTitle.equals(game, ignoreCase = true)
        } ?: PresetPatches.allPresets.first()

        _uiState.value = _uiState.value.copy(
            favoriteGame = game,
            favoriteHeroes = newFavs,
            catchupGameTitle = game,
            selectedGenre = matchingPreset.genre,
            gameTitle = matchingPreset.gameTitle,
            patchVersion = matchingPreset.patchVersion,
            patchNotesText = matchingPreset.rawPatchNotes,
            toastMessage = "Switched primary game to $game"
        )
        updateCatchupReport(game, _uiState.value.catchupYear, _uiState.value.catchupMonth)
        analyzePatch()
    }

    fun toggleFavoriteHero(heroName: String) {
        val current = _uiState.value.favoriteHeroes.toMutableSet()
        if (current.contains(heroName)) {
            current.remove(heroName)
        } else {
            current.add(heroName)
        }
        prefsRepo.setFavoriteHeroes(current)
        _uiState.value = _uiState.value.copy(favoriteHeroes = current)
    }

    fun setThemeSetting(theme: ThemeSetting) {
        prefsRepo.setThemeSetting(theme)
        _uiState.value = _uiState.value.copy(themeSetting = theme)
    }

    fun setShowPreferencesDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(showPreferencesDialog = show)
    }

    fun setShowOnboardingDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(showOnboardingDialog = show)
    }

    fun setGenreFilter(genre: GameGenre?) {
        _uiState.value = _uiState.value.copy(genreFilter = genre)
    }

    fun setHomeSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(homeSearchQuery = query)
    }

    fun setCatchupGame(game: String) {
        _uiState.value = _uiState.value.copy(catchupGameTitle = game)
        updateCatchupReport(game, _uiState.value.catchupYear, _uiState.value.catchupMonth)
    }

    fun setCatchupYear(year: Int) {
        _uiState.value = _uiState.value.copy(catchupYear = year)
        updateCatchupReport(_uiState.value.catchupGameTitle, year, _uiState.value.catchupMonth)
    }

    fun setCatchupMonth(month: Int?) {
        _uiState.value = _uiState.value.copy(catchupMonth = month)
        updateCatchupReport(_uiState.value.catchupGameTitle, _uiState.value.catchupYear, month)
    }

    private fun updateCatchupReport(game: String, year: Int, month: Int?) {
        val report = PatchTimelineEngine.calculateCatchup(game, year, month)
        _uiState.value = _uiState.value.copy(catchupReport = report)
    }

    fun applyCatchupToAnalysis() {
        val catchup = _uiState.value.catchupReport ?: return
        val genre = when {
            catchup.gameTitle.contains("League", true) || catchup.gameTitle.contains("Dota", true) -> GameGenre.MOBA
            catchup.gameTitle.contains("Apex", true) -> GameGenre.BATTLE_ROYALE
            catchup.gameTitle.contains("Overwatch", true) -> GameGenre.HYBRID
            else -> GameGenre.SHOOTER
        }

        _uiState.value = _uiState.value.copy(
            selectedGenre = genre,
            gameTitle = catchup.gameTitle,
            patchVersion = "Catch-Up vs ${catchup.earliestBaselinePatch}",
            patchNotesText = catchup.rawComparisonPatchNotes,
            toastMessage = "Loaded timeline catch-up for ${catchup.gameTitle} (${catchup.earliestBaselinePatch})!"
        )
        analyzePatch()
    }

    fun selectGenre(genre: GameGenre) {
        val defaultGame = when (genre) {
            GameGenre.SHOOTER -> "Valorant"
            GameGenre.MOBA -> "League of Legends"
            GameGenre.BATTLE_ROYALE -> "Apex Legends"
            GameGenre.HYBRID -> "Overwatch 2"
        }
        _uiState.value = _uiState.value.copy(
            selectedGenre = genre,
            gameTitle = defaultGame
        )
    }

    fun setGameTitle(title: String) {
        _uiState.value = _uiState.value.copy(gameTitle = title)
    }

    fun setPatchVersion(version: String) {
        _uiState.value = _uiState.value.copy(patchVersion = version)
    }

    fun setPatchNotesText(text: String) {
        _uiState.value = _uiState.value.copy(patchNotesText = text)
    }

    fun loadPreset(preset: PresetPatchNote) {
        _uiState.value = _uiState.value.copy(
            selectedGenre = preset.genre,
            gameTitle = preset.gameTitle,
            patchVersion = preset.patchVersion,
            patchNotesText = preset.rawPatchNotes,
            toastMessage = "Loaded preset: ${preset.gameTitle} ${preset.patchVersion}"
        )
    }

    fun clearInput() {
        _uiState.value = _uiState.value.copy(
            patchNotesText = "",
            patchVersion = ""
        )
    }

    fun setSelectedTab(tab: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    fun setAnalysisSectionTab(tab: Int) {
        _uiState.value = _uiState.value.copy(analysisSectionTab = tab)
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun clearToast() {
        _uiState.value = _uiState.value.copy(toastMessage = null)
    }

    fun analyzePatch() {
        val state = _uiState.value
        if (state.patchNotesText.isBlank()) {
            _uiState.value = state.copy(toastMessage = "Please provide patch notes or select a preset.")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAnalyzing = true)
            try {
                val report = analystEngine.analyzePatch(
                    gameTitle = state.gameTitle.ifBlank { state.favoriteGame },
                    genre = state.selectedGenre,
                    patchVersion = state.patchVersion.ifBlank { "Live Update" },
                    rawNotes = state.patchNotesText
                )
                _uiState.value = _uiState.value.copy(
                    isAnalyzing = false,
                    currentReport = report,
                    isSaved = false,
                    selectedTab = 1, // Switch to result screen
                    analysisSectionTab = 0,
                    toastMessage = "Meta shift analysis compiled!"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isAnalyzing = false,
                    toastMessage = "Analysis error: ${e.localizedMessage}"
                )
            }
        }
    }

    fun toggleSaveCurrentReport() {
        val report = _uiState.value.currentReport ?: return
        viewModelScope.launch {
            if (_uiState.value.isSaved) {
                dao.deletePatchById(report.id)
                _uiState.value = _uiState.value.copy(isSaved = false, toastMessage = "Removed from Saved Vault")
            } else {
                dao.insertPatch(SavedPatchEntity.fromDomain(report))
                _uiState.value = _uiState.value.copy(isSaved = true, toastMessage = "Saved to Vault")
            }
        }
    }

    fun openSavedReport(report: PatchAnalysisReport) {
        val genre = try {
            GameGenre.valueOf(report.genre)
        } catch (e: Exception) {
            GameGenre.SHOOTER
        }
        _uiState.value = _uiState.value.copy(
            currentReport = report,
            gameTitle = report.gameTitle,
            selectedGenre = genre,
            patchVersion = report.patchVersion,
            isSaved = true,
            selectedTab = 1,
            analysisSectionTab = 0
        )
    }

    fun deleteSavedReport(reportId: String) {
        viewModelScope.launch {
            dao.deletePatchById(reportId)
            if (_uiState.value.currentReport?.id == reportId) {
                _uiState.value = _uiState.value.copy(isSaved = false)
            }
            _uiState.value = _uiState.value.copy(toastMessage = "Report deleted")
        }
    }
}
