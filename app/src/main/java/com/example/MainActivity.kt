package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.ThemeSetting
import com.example.ui.components.PersonalizationOnboardingDialog
import com.example.ui.components.PreferencesDialog
import com.example.ui.screens.AnalysisResultScreen
import com.example.ui.screens.AnalyzeInputScreen
import com.example.ui.screens.SavedVaultScreen
import com.example.ui.theme.ElegantBorder
import com.example.ui.theme.ElegantDarkBg
import com.example.ui.theme.ElegantOnPrimary
import com.example.ui.theme.ElegantPrimary
import com.example.ui.theme.ElegantSurface
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MetaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MetaViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            MyApplicationTheme(themeSetting = uiState.themeSetting) {
                MainApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    viewModel: MetaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val savedReports by viewModel.savedReports.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearToast()
        }
    }

    // First Open Onboarding Dialog
    if (uiState.showOnboardingDialog) {
        PersonalizationOnboardingDialog(
            initialGame = uiState.favoriteGame,
            onComplete = { game, heroes ->
                viewModel.completeOnboarding(game, heroes)
            },
            onDismiss = {
                viewModel.setShowOnboardingDialog(false)
            }
        )
    }

    // Preferences & Theme Dialog
    if (uiState.showPreferencesDialog) {
        PreferencesDialog(
            selectedGame = uiState.favoriteGame,
            favoriteHeroes = uiState.favoriteHeroes,
            themeSetting = uiState.themeSetting,
            onGameSelected = { viewModel.setFavoriteGame(it) },
            onToggleHero = { viewModel.toggleFavoriteHero(it) },
            onThemeSelected = { viewModel.setThemeSetting(it) },
            onDismiss = { viewModel.setShowPreferencesDialog(false) }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "PatchMeta",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(10.dp))

                        // Quick Game Switcher Badge
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            modifier = Modifier
                                .clickable { viewModel.setShowPreferencesDialog(true) }
                                .testTag("top_bar_game_switcher")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFFFD54F),
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = uiState.favoriteGame,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 11.sp
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Switch game",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                },
                actions = {
                    // Quick Theme Toggle Button (Cycles System -> Dark -> Light)
                    IconButton(
                        onClick = {
                            val nextTheme = when (uiState.themeSetting) {
                                ThemeSetting.SYSTEM -> ThemeSetting.DARK
                                ThemeSetting.DARK -> ThemeSetting.LIGHT
                                ThemeSetting.LIGHT -> ThemeSetting.SYSTEM
                            }
                            viewModel.setThemeSetting(nextTheme)
                        },
                        modifier = Modifier.testTag("quick_theme_toggle_btn")
                    ) {
                        Icon(
                            imageVector = when (uiState.themeSetting) {
                                ThemeSetting.LIGHT -> Icons.Default.LightMode
                                ThemeSetting.DARK -> Icons.Default.DarkMode
                                ThemeSetting.SYSTEM -> Icons.Default.BrightnessAuto
                            },
                            contentDescription = "Toggle theme mode (${uiState.themeSetting.name})",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    // Preferences & Profile Button
                    IconButton(
                        onClick = { viewModel.setShowPreferencesDialog(true) },
                        modifier = Modifier.testTag("top_bar_preferences_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Preferences & Profile",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)
                    )
                    .testTag("bottom_nav_bar")
            ) {
                NavigationBarItem(
                    selected = uiState.selectedTab == 0,
                    onClick = { viewModel.setSelectedTab(0) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Studio / Input"
                        )
                    },
                    label = {
                        Text(
                            text = "Analyze",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (uiState.selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 11.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_input")
                )

                NavigationBarItem(
                    selected = uiState.selectedTab == 1,
                    onClick = {
                        if (uiState.currentReport != null) {
                            viewModel.setSelectedTab(1)
                        } else {
                            viewModel.analyzePatch()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Assessment,
                            contentDescription = "Analysis Report"
                        )
                    },
                    label = {
                        Text(
                            text = "Meta Report",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (uiState.selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 11.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_report")
                )

                NavigationBarItem(
                    selected = uiState.selectedTab == 2,
                    onClick = { viewModel.setSelectedTab(2) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Saved Vault"
                        )
                    },
                    label = {
                        Text(
                            text = "Vault (${savedReports.size})",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (uiState.selectedTab == 2) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 11.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_vault")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (uiState.selectedTab) {
                0 -> {
                    AnalyzeInputScreen(
                        state = uiState,
                        onGenreFilterChanged = { viewModel.setGenreFilter(it) },
                        onSearchFilterChanged = { viewModel.setHomeSearchQuery(it) },
                        onCatchupGameChanged = { viewModel.setCatchupGame(it) },
                        onCatchupYearChanged = { viewModel.setCatchupYear(it) },
                        onCatchupMonthChanged = { viewModel.setCatchupMonth(it) },
                        onApplyCatchup = { viewModel.applyCatchupToAnalysis() },
                        onGameTitleChanged = { viewModel.setGameTitle(it) },
                        onPatchVersionChanged = { viewModel.setPatchVersion(it) },
                        onPatchNotesChanged = { viewModel.setPatchNotesText(it) },
                        onLoadPreset = { viewModel.loadPreset(it) },
                        onClearInput = { viewModel.clearInput() },
                        onAnalyzeClicked = { viewModel.analyzePatch() },
                        onManageFavorites = { viewModel.setShowPreferencesDialog(true) }
                    )
                }

                1 -> {
                    val report = uiState.currentReport
                    if (report != null) {
                        AnalysisResultScreen(
                            report = report,
                            isSaved = uiState.isSaved,
                            activeSubTab = uiState.analysisSectionTab,
                            favoriteHeroes = uiState.favoriteHeroes,
                            onSubTabChanged = { viewModel.setAnalysisSectionTab(it) },
                            onToggleSave = { viewModel.toggleSaveCurrentReport() },
                            onBackToInput = { viewModel.setSelectedTab(0) },
                            onManageFavorites = { viewModel.setShowPreferencesDialog(true) }
                        )
                    } else {
                        AnalyzeInputScreen(
                            state = uiState,
                            onGenreFilterChanged = { viewModel.setGenreFilter(it) },
                            onSearchFilterChanged = { viewModel.setHomeSearchQuery(it) },
                            onCatchupGameChanged = { viewModel.setCatchupGame(it) },
                            onCatchupYearChanged = { viewModel.setCatchupYear(it) },
                            onCatchupMonthChanged = { viewModel.setCatchupMonth(it) },
                            onApplyCatchup = { viewModel.applyCatchupToAnalysis() },
                            onGameTitleChanged = { viewModel.setGameTitle(it) },
                            onPatchVersionChanged = { viewModel.setPatchVersion(it) },
                            onPatchNotesChanged = { viewModel.setPatchNotesText(it) },
                            onLoadPreset = { viewModel.loadPreset(it) },
                            onClearInput = { viewModel.clearInput() },
                            onAnalyzeClicked = { viewModel.analyzePatch() },
                            onManageFavorites = { viewModel.setShowPreferencesDialog(true) }
                        )
                    }
                }

                2 -> {
                    SavedVaultScreen(
                        savedReports = savedReports,
                        searchQuery = uiState.searchQuery,
                        onSearchQueryChanged = { viewModel.setSearchQuery(it) },
                        onOpenReport = { viewModel.openSavedReport(it) },
                        onDeleteReport = { viewModel.deleteSavedReport(it) },
                        onGoToAnalyze = { viewModel.setSelectedTab(0) }
                    )
                }
            }
        }
    }
}
