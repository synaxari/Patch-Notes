package com.example.ui.screens

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameGenre
import com.example.data.model.PresetPatchNote
import com.example.data.model.PresetPatches
import com.example.ui.components.FavoriteHeroesStatusCard
import com.example.ui.theme.ElegantBorder
import com.example.ui.theme.ElegantBorderLight
import com.example.ui.theme.ElegantOnPrimary
import com.example.ui.theme.ElegantPrimary
import com.example.ui.theme.ElegantSecondary
import com.example.ui.theme.ElegantSurface
import com.example.ui.theme.ElegantSurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.MetaUiState

@Composable
fun AnalyzeInputScreen(
    state: MetaUiState,
    onGenreFilterChanged: (GameGenre?) -> Unit,
    onSearchFilterChanged: (String) -> Unit,
    onCatchupGameChanged: (String) -> Unit,
    onCatchupYearChanged: (Int) -> Unit,
    onCatchupMonthChanged: (Int?) -> Unit,
    onApplyCatchup: () -> Unit,
    onGameTitleChanged: (String) -> Unit,
    onPatchVersionChanged: (String) -> Unit,
    onPatchNotesChanged: (String) -> Unit,
    onLoadPreset: (PresetPatchNote) -> Unit,
    onClearInput: () -> Unit,
    onAnalyzeClicked: () -> Unit,
    onManageFavorites: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val filteredPresets = PresetPatches.allPresets.filter { preset ->
        val matchesGenre = state.genreFilter == null || preset.genre == state.genreFilter
        val matchesSearch = state.homeSearchQuery.isBlank() ||
                preset.gameTitle.contains(state.homeSearchQuery, ignoreCase = true) ||
                preset.patchVersion.contains(state.homeSearchQuery, ignoreCase = true) ||
                preset.title.contains(state.homeSearchQuery, ignoreCase = true) ||
                preset.shortSummary.contains(state.homeSearchQuery, ignoreCase = true)
        matchesGenre && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Hero Header Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(ElegantSurface)
                .border(1.dp, ElegantBorder, RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(ElegantPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = ElegantOnPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "ESPORTS META INTEL",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElegantPrimary,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Competitive Patch Analyst",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = ElegantSurfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder)
                    ) {
                        Text(
                            text = "LIVE DATA ENGINE",
                            color = ElegantSecondary,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Track ability kit reworks with visual tooltips, weapon TTKs, returning player timeline evolutions, and tournament win-rate forecasts.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Prominent Signature Heroes Meta Status
        FavoriteHeroesStatusCard(
            gameTitle = state.favoriteGame,
            favoriteHeroNames = state.favoriteHeroes,
            currentReport = state.currentReport,
            onManageFavoritesClick = onManageFavorites
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar for Game Title or Patch Notes
        OutlinedTextField(
            value = state.homeSearchQuery,
            onValueChange = onSearchFilterChanged,
            placeholder = { Text("Search game title (Valorant, League, Apex, Dota...) or patch...", color = TextTertiary, fontSize = 13.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = ElegantSecondary
                )
            },
            trailingIcon = {
                if (state.homeSearchQuery.isNotBlank()) {
                    IconButton(onClick = { onSearchFilterChanged("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search",
                            tint = TextSecondary
                        )
                    }
                }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("home_search_field"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ElegantPrimary,
                unfocusedBorderColor = ElegantBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedContainerColor = ElegantSurface,
                unfocusedContainerColor = ElegantSurface
            ),
            shape = RoundedCornerShape(18.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Competitive Genre Selector Chips
        Text(
            text = "SELECT COMPETITIVE GENRE",
            style = MaterialTheme.typography.labelSmall,
            color = ElegantPrimary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val isAllSelected = state.genreFilter == null
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isAllSelected) ElegantPrimary else ElegantSurface,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isAllSelected) ElegantPrimary else ElegantBorder
                ),
                modifier = Modifier
                    .clickable { onGenreFilterChanged(null) }
                    .testTag("genre_chip_all")
            ) {
                Text(
                    text = "All Genres",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isAllSelected) ElegantOnPrimary else TextPrimary,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }

            GameGenre.values().forEach { genre ->
                val isSelected = state.genreFilter == genre
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) ElegantPrimary else ElegantSurface,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) ElegantPrimary else ElegantBorder
                    ),
                    modifier = Modifier
                        .clickable { onGenreFilterChanged(genre) }
                        .testTag("genre_chip_${genre.name}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when (genre) {
                                GameGenre.SHOOTER -> Icons.Default.SportsEsports
                                GameGenre.MOBA -> Icons.Default.Bolt
                                GameGenre.BATTLE_ROYALE -> Icons.Default.Shield
                                GameGenre.HYBRID -> Icons.Default.AutoAwesome
                            },
                            contentDescription = null,
                            tint = if (isSelected) ElegantOnPrimary else ElegantSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = genre.displayName,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) ElegantOnPrimary else TextPrimary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // RETURNING PLAYER TIMELINE CATCH-UP MODULE
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("returning_player_card"),
            colors = CardDefaults.cardColors(containerColor = ElegantSurface),
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorderLight)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x33D0BCFF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = ElegantPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "RETURNING PLAYER CATCH-UP",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElegantPrimary,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                            Text(
                                text = "Find What Changed Since You Left",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = ElegantSurfaceVariant
                    ) {
                        Text(
                            text = "TIMELINE LOCATOR",
                            color = ElegantSecondary,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Can't remember which patch you stopped playing at? Enter your game, the year (and optional month) you stepped away, and we locate the earliest patch to compare changes.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Game Title Selector Chips
                Text(
                    text = "SELECT COMPETITIVE GAME",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val supportedGames = listOf("Valorant", "League of Legends", "Apex Legends", "Dota 2", "Overwatch 2")
                    supportedGames.forEach { game ->
                        val isSelected = state.catchupGameTitle.equals(game, ignoreCase = true)
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) ElegantPrimary else ElegantSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) ElegantPrimary else ElegantBorder),
                            modifier = Modifier.clickable { onCatchupGameChanged(game) }
                        ) {
                            Text(
                                text = game,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) ElegantOnPrimary else TextPrimary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Year Selector Chips
                Text(
                    text = "YEAR LAST PLAYED",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val years = listOf(2023, 2024, 2025, 2026)
                    years.forEach { year ->
                        val isSelected = state.catchupYear == year
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) ElegantPrimary else ElegantSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) ElegantPrimary else ElegantBorder),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onCatchupYearChanged(year) }
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Text(
                                    text = year.toString(),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) ElegantOnPrimary else TextPrimary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Month Selector (Optional)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "MONTH LAST PLAYED (OPTIONAL)",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = if (state.catchupMonth == null) "All / Any Month" else "Month ${state.catchupMonth}",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElegantSecondary,
                        fontSize = 10.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val isAnyMonth = state.catchupMonth == null
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isAnyMonth) ElegantSecondary else ElegantSurfaceVariant,
                        modifier = Modifier.clickable { onCatchupMonthChanged(null) }
                    ) {
                        Text(
                            text = "Any Month",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isAnyMonth) FontWeight.Bold else FontWeight.Medium,
                            color = if (isAnyMonth) Color.Black else TextPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }

                    val months = listOf(
                        1 to "Jan", 2 to "Feb", 3 to "Mar", 4 to "Apr",
                        5 to "May", 6 to "Jun", 7 to "Jul", 8 to "Aug",
                        9 to "Sep", 10 to "Oct", 11 to "Nov", 12 to "Dec"
                    )
                    months.forEach { (mNum, mName) ->
                        val isSelected = state.catchupMonth == mNum
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) ElegantPrimary else ElegantSurfaceVariant,
                            modifier = Modifier.clickable { onCatchupMonthChanged(mNum) }
                        ) {
                            Text(
                                text = mName,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) ElegantOnPrimary else TextPrimary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                // Calculated Catch-up Evolution Summary Card
                state.catchupReport?.let { catchup ->
                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF141318),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "EARLIEST IDENTIFIED PATCH",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = ElegantPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                    Text(
                                        text = catchup.earliestBaselinePatch,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = ElegantSurfaceVariant
                                ) {
                                    Text(
                                        text = "~${catchup.estimatedPatchesBehind} Patches Behind",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = ElegantSecondary,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Major Ability Reworks Since You Left:",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            catchup.majorReworkHighlights.take(3).forEach { rework ->
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "• ",
                                        color = ElegantPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = rework,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextPrimary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Action Button: Load & Compare Evolution
                            Button(
                                onClick = onApplyCatchup,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("load_catchup_evolution_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = ElegantPrimary),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = ElegantOnPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Compare Changes (${catchup.earliestBaselinePatch.take(18)}... vs Live)",
                                    color = ElegantOnPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Preset Real Patches Quick Loader
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SAMPLE OFFICIAL PATCH NOTES (${filteredPresets.size})",
                style = MaterialTheme.typography.labelSmall,
                color = ElegantSecondary,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )

            if (state.homeSearchQuery.isNotBlank() || state.genreFilter != null) {
                Text(
                    text = "Filtered",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElegantPrimary,
                    fontSize = 11.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (filteredPresets.isEmpty()) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = ElegantSurface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "No preset patches found for your search. Try clearing the filter or selecting 'All Genres'.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                filteredPresets.forEach { preset ->
                    val isCurrent = state.gameTitle.equals(preset.gameTitle, true) && state.patchVersion.equals(preset.patchVersion, true)
                    Card(
                        modifier = Modifier
                            .width(220.dp)
                            .clickable { onLoadPreset(preset) }
                            .testTag("preset_card_${preset.id}"),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCurrent) ElegantSurfaceVariant else ElegantSurface
                        ),
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isCurrent) ElegantPrimary else ElegantBorder
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = preset.gameTitle,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = ElegantPrimary
                                )
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = ElegantSurfaceVariant
                                ) {
                                    Text(
                                        text = preset.patchVersion,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = ElegantSecondary,
                                        fontSize = 10.sp,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = preset.title,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = preset.shortSummary,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary,
                                fontSize = 11.sp,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Title and Version Fields
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = state.gameTitle,
                onValueChange = onGameTitleChanged,
                label = { Text("Active Game Title", color = TextSecondary) },
                singleLine = true,
                modifier = Modifier
                    .weight(1.3f)
                    .testTag("input_game_title"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElegantPrimary,
                    unfocusedBorderColor = ElegantBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = ElegantSurface,
                    unfocusedContainerColor = ElegantSurface
                ),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = state.patchVersion,
                onValueChange = onPatchVersionChanged,
                label = { Text("Version / Tag", color = TextSecondary) },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .testTag("input_patch_version"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElegantPrimary,
                    unfocusedBorderColor = ElegantBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = ElegantSurface,
                    unfocusedContainerColor = ElegantSurface
                ),
                shape = RoundedCornerShape(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Patch Notes Input Area
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "RAW PATCH NOTES FOR ANALYSIS",
                style = MaterialTheme.typography.labelSmall,
                color = ElegantPrimary,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )

            Row {
                IconButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                        val clip = clipboard?.primaryClip?.getItemAt(0)?.text?.toString()
                        if (!clip.isNullOrBlank()) {
                            onPatchNotesChanged(clip)
                        }
                    },
                    modifier = Modifier.size(36.dp).testTag("paste_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentPaste,
                        contentDescription = "Paste from Clipboard",
                        tint = ElegantPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                if (state.patchNotesText.isNotBlank()) {
                    IconButton(
                        onClick = onClearInput,
                        modifier = Modifier.size(36.dp).testTag("clear_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear Input",
                            tint = TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = state.patchNotesText,
            onValueChange = onPatchNotesChanged,
            placeholder = {
                Text(
                    "Paste official patch notes here...\n\nExample:\n- Double Tap (E): Iso now grants himself a Shield instantly without needing a kill or orb.\n- High Gear (E): Neon can now slide twice per activation with 100% weapon accuracy.",
                    color = TextTertiary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .testTag("input_patch_notes"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ElegantPrimary,
                unfocusedBorderColor = ElegantBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedContainerColor = ElegantSurface,
                unfocusedContainerColor = ElegantSurface
            ),
            shape = RoundedCornerShape(18.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Analyze Action Button
        Button(
            onClick = onAnalyzeClicked,
            enabled = !state.isAnalyzing,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("analyze_action_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = ElegantPrimary,
                disabledContainerColor = ElegantSurfaceVariant
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            if (state.isAnalyzing) {
                CircularProgressIndicator(
                    color = ElegantOnPrimary,
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.5.dp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Compiling Meta Shift Analysis...",
                    color = ElegantOnPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = ElegantOnPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Compile Meta Shift Analysis",
                    color = ElegantOnPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
