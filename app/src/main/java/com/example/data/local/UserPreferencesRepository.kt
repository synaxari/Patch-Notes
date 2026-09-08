package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class ThemeSetting {
    SYSTEM,
    DARK,
    LIGHT
}

data class UserPreferences(
    val favoriteGame: String? = null,
    val favoriteHeroes: Set<String> = emptySet(),
    val themeSetting: ThemeSetting = ThemeSetting.SYSTEM,
    val hasCompletedOnboarding: Boolean = false
)

class UserPreferencesRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("esports_meta_prefs", Context.MODE_PRIVATE)

    private val _preferences = MutableStateFlow(loadFromPrefs())
    val preferences: StateFlow<UserPreferences> = _preferences.asStateFlow()

    private fun loadFromPrefs(): UserPreferences {
        val favGame = prefs.getString(KEY_FAVORITE_GAME, null)
        val favHeroes = prefs.getStringSet(KEY_FAVORITE_HEROES, emptySet()) ?: emptySet()
        val themeStr = prefs.getString(KEY_THEME_SETTING, ThemeSetting.SYSTEM.name) ?: ThemeSetting.SYSTEM.name
        val theme = runCatching { ThemeSetting.valueOf(themeStr) }.getOrDefault(ThemeSetting.SYSTEM)
        val onboarding = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)

        return UserPreferences(
            favoriteGame = favGame,
            favoriteHeroes = favHeroes,
            themeSetting = theme,
            hasCompletedOnboarding = onboarding
        )
    }

    fun setFavoriteGame(game: String) {
        prefs.edit().putString(KEY_FAVORITE_GAME, game).apply()
        _preferences.value = _preferences.value.copy(favoriteGame = game)
    }

    fun setFavoriteHeroes(heroes: Set<String>) {
        prefs.edit().putStringSet(KEY_FAVORITE_HEROES, heroes).apply()
        _preferences.value = _preferences.value.copy(favoriteHeroes = heroes)
    }

    fun toggleFavoriteHero(heroName: String) {
        val current = _preferences.value.favoriteHeroes.toMutableSet()
        if (current.contains(heroName)) {
            current.remove(heroName)
        } else {
            current.add(heroName)
        }
        setFavoriteHeroes(current)
    }

    fun setThemeSetting(theme: ThemeSetting) {
        prefs.edit().putString(KEY_THEME_SETTING, theme.name).apply()
        _preferences.value = _preferences.value.copy(themeSetting = theme)
    }

    fun completeOnboarding(favoriteGame: String, favoriteHeroes: Set<String>) {
        prefs.edit()
            .putString(KEY_FAVORITE_GAME, favoriteGame)
            .putStringSet(KEY_FAVORITE_HEROES, favoriteHeroes)
            .putBoolean(KEY_ONBOARDING_COMPLETED, true)
            .apply()

        _preferences.value = _preferences.value.copy(
            favoriteGame = favoriteGame,
            favoriteHeroes = favoriteHeroes,
            hasCompletedOnboarding = true
        )
    }

    fun resetOnboarding() {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, false).apply()
        _preferences.value = _preferences.value.copy(hasCompletedOnboarding = false)
    }

    companion object {
        private const val KEY_FAVORITE_GAME = "key_fav_game"
        private const val KEY_FAVORITE_HEROES = "key_fav_heroes"
        private const val KEY_THEME_SETTING = "key_theme_setting"
        private const val KEY_ONBOARDING_COMPLETED = "key_onboarding_completed"
    }
}
