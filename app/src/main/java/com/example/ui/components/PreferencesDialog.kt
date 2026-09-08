package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.ThemeSetting
import com.example.data.model.HeroRoster
import com.example.ui.theme.BuffGreen
import com.example.ui.theme.ElegantBorder
import com.example.ui.theme.ElegantDarkBg
import com.example.ui.theme.ElegantOnPrimary
import com.example.ui.theme.ElegantPrimary
import com.example.ui.theme.ElegantSecondary
import com.example.ui.theme.ElegantSurface
import com.example.ui.theme.ElegantSurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PreferencesDialog(
    selectedGame: String,
    favoriteHeroes: Set<String>,
    themeSetting: ThemeSetting,
    onGameSelected: (String) -> Unit,
    onToggleHero: (String) -> Unit,
    onThemeSelected: (ThemeSetting) -> Unit,
    onDismiss: () -> Unit
) {
    val availableHeroes = HeroRoster.getHeroesForGame(selectedGame)
    val availableRoles = HeroRoster.getAllHeroRolesForGame(selectedGame)

    var heroSearchQuery by remember(selectedGame) { mutableStateOf("") }
    var selectedRoleFilter by remember(selectedGame) { mutableStateOf("All") }

    val filteredHeroes = availableHeroes.filter { hero ->
        val matchesQuery = heroSearchQuery.isBlank() ||
                hero.name.contains(heroSearchQuery, ignoreCase = true) ||
                hero.role.contains(heroSearchQuery, ignoreCase = true) ||
                hero.signatureAbility.contains(heroSearchQuery, ignoreCase = true)
        val matchesRole = selectedRoleFilter == "All" ||
                hero.role.contains(selectedRoleFilter, ignoreCase = true)
        matchesQuery && matchesRole
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .testTag("preferences_dialog"),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = ElegantDarkBg),
            border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0x33D0BCFF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = ElegantPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Settings & Personalization",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Theme, Primary Game & Signature Mains",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_preferences_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Section 1: Appearance / Theme Mode
                Text(
                    text = "THEME & APPEARANCE",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElegantPrimary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ThemeOptionCard(
                        title = "System",
                        subtitle = "Match device",
                        icon = Icons.Default.BrightnessAuto,
                        isSelected = themeSetting == ThemeSetting.SYSTEM,
                        onClick = { onThemeSelected(ThemeSetting.SYSTEM) },
                        modifier = Modifier.weight(1f)
                    )
                    ThemeOptionCard(
                        title = "Dark",
                        subtitle = "OLED Black",
                        icon = Icons.Default.DarkMode,
                        isSelected = themeSetting == ThemeSetting.DARK,
                        onClick = { onThemeSelected(ThemeSetting.DARK) },
                        modifier = Modifier.weight(1f)
                    )
                    ThemeOptionCard(
                        title = "Light",
                        subtitle = "High contrast",
                        icon = Icons.Default.LightMode,
                        isSelected = themeSetting == ThemeSetting.LIGHT,
                        onClick = { onThemeSelected(ThemeSetting.LIGHT) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Section 2: Favorite Game
                Text(
                    text = "PRIMARY COMPETITIVE GAME",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElegantPrimary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
                Text(
                    text = "Your favorite game opens automatically and shapes your meta feeds.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HeroRoster.supportedGames.forEach { game ->
                        val isSelected = game.equals(selectedGame, ignoreCase = true)
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) ElegantPrimary else ElegantSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) ElegantPrimary else ElegantBorder
                            ),
                            modifier = Modifier
                                .clickable { onGameSelected(game) }
                                .testTag("select_game_pref_$game")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = ElegantOnPrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                Text(
                                    text = game,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) ElegantOnPrimary else TextPrimary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Section 3: Favorite Heroes for Selected Game
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SIGNATURE HEROES ($selectedGame)",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElegantPrimary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (favoriteHeroes.isNotEmpty()) Color(0x33D0BCFF) else ElegantSurfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (favoriteHeroes.isNotEmpty()) ElegantPrimary else ElegantBorder)
                    ) {
                        Text(
                            text = "${favoriteHeroes.size} tracked",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (favoriteHeroes.isNotEmpty()) ElegantPrimary else TextSecondary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = "Select any of the ${availableHeroes.size} characters in $selectedGame to monitor meta viability and patch notes.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Hero Search Bar
                OutlinedTextField(
                    value = heroSearchQuery,
                    onValueChange = { heroSearchQuery = it },
                    placeholder = {
                        Text(
                            "Search all ${availableHeroes.size} characters...",
                            color = TextSecondary.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search heroes",
                            tint = ElegantSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    trailingIcon = {
                        if (heroSearchQuery.isNotBlank()) {
                            IconButton(onClick = { heroSearchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search",
                                    tint = TextSecondary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("preferences_hero_search_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElegantPrimary,
                        unfocusedBorderColor = ElegantBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedContainerColor = ElegantSurface,
                        unfocusedContainerColor = ElegantSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Role Filter Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    availableRoles.forEach { role ->
                        val isSelectedRole = role == selectedRoleFilter
                        FilterChip(
                            selected = isSelectedRole,
                            onClick = { selectedRoleFilter = role },
                            label = {
                                Text(
                                    text = role,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelectedRole) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ElegantPrimary,
                                selectedLabelColor = ElegantOnPrimary,
                                containerColor = ElegantSurfaceVariant,
                                labelColor = TextPrimary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = if (isSelectedRole) ElegantPrimary else ElegantBorder,
                                enabled = true,
                                selected = isSelectedRole
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Count & Reset
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (heroSearchQuery.isBlank() && selectedRoleFilter == "All") {
                            "All ${availableHeroes.size} characters available"
                        } else {
                            "Showing ${filteredHeroes.size} of ${availableHeroes.size} characters"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )

                    if (heroSearchQuery.isNotBlank() || selectedRoleFilter != "All") {
                        Text(
                            text = "Reset filter",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElegantPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier
                                .clickable {
                                    heroSearchQuery = ""
                                    selectedRoleFilter = "All"
                                }
                                .padding(2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (filteredHeroes.isEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = ElegantSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No characters matching \"$heroSearchQuery\"",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedButton(
                                onClick = {
                                    heroSearchQuery = ""
                                    selectedRoleFilter = "All"
                                },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Show All", fontSize = 11.sp)
                            }
                        }
                    }
                } else {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        filteredHeroes.forEach { hero ->
                            val isFav = favoriteHeroes.contains(hero.name)
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isFav) Color(0x33D0BCFF) else ElegantSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isFav) ElegantPrimary else ElegantBorder
                                ),
                                modifier = Modifier
                                    .clickable { onToggleHero(hero.name) }
                                    .testTag("toggle_hero_pref_${hero.name}")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clip(CircleShape)
                                            .background(if (isFav) ElegantPrimary else Color(0x33938F99)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isFav) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color(0xFF381E72),
                                                modifier = Modifier.size(12.dp)
                                            )
                                        } else {
                                            Text(
                                                text = hero.name.take(1),
                                                color = TextSecondary,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(
                                            text = hero.name,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = if (isFav) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isFav) TextPrimary else TextPrimary.copy(alpha = 0.85f),
                                            fontSize = 12.sp
                                        )
                                        Text(
                                            text = "${hero.role} • ${hero.defaultTier}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (isFav) ElegantPrimary else TextSecondary,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("save_preferences_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = ElegantPrimary),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "Done (${favoriteHeroes.size} Mains Tracked)",
                        color = ElegantOnPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeOptionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) Color(0x33D0BCFF) else ElegantSurfaceVariant,
        border = androidx.compose.foundation.BorderStroke(
            if (isSelected) 1.5.dp else 1.dp,
            if (isSelected) ElegantPrimary else ElegantBorder
        ),
        modifier = modifier
            .clickable { onClick() }
            .testTag("theme_option_$title")
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) ElegantPrimary else Color(0x22FFFFFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) ElegantOnPrimary else TextSecondary,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) ElegantPrimary else TextPrimary,
                fontSize = 12.sp
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                fontSize = 9.sp
            )
        }
    }
}
