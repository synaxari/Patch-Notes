package com.example.ui.components

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.HeroRoster
import com.example.ui.theme.BuffGreen
import com.example.ui.theme.ElegantBorder
import com.example.ui.theme.ElegantBorderLight
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
fun PersonalizationOnboardingDialog(
    initialGame: String = "Valorant",
    onComplete: (game: String, heroes: Set<String>) -> Unit,
    onDismiss: () -> Unit
) {
    var step by remember { mutableIntStateOf(1) } // 1: Game, 2: Heroes
    var selectedGame by remember { mutableStateOf(initialGame) }
    var selectedHeroes by remember {
        val initialRoster = HeroRoster.getHeroesForGame(initialGame)
        mutableStateOf(initialRoster.take(2).map { it.name }.toSet())
    }

    // Step 2 Search & Filter state
    var heroSearchQuery by remember { mutableStateOf("") }
    var selectedRoleFilter by remember { mutableStateOf("All") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .testTag("onboarding_dialog"),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = ElegantDarkBg),
            border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                // Header badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0x33D0BCFF),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ElegantPrimary)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFD54F),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "PERSONALIZED META HUB",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElegantPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    // Step Indicator
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(
                            modifier = Modifier
                                .size(width = 24.dp, height = 6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(if (step >= 1) ElegantPrimary else Color(0x33938F99))
                        )
                        Box(
                            modifier = Modifier
                                .size(width = 24.dp, height = 6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(if (step >= 2) ElegantPrimary else Color(0x33938F99))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                AnimatedContent(targetState = step, label = "OnboardingStepAnimation") { targetStep ->
                    if (targetStep == 1) {
                        // STEP 1: Select Game
                        Column {
                            Text(
                                text = "STEP 1 OF 2: YOUR PRIMARY TITLE",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElegantSecondary,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "What game are you currently playing?",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "We'll tailor your homescreen, patch alerts, and meta reports around your main title.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                lineHeight = 20.sp
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            val games = listOf(
                                Triple("Valorant", "Tactical 5v5 Hero FPS", "Duelists, Controllers & Site Retakes"),
                                Triple("Apex Legends", "Battle Royale & Skirmishers", "Mobility, Shield Swapping & Ring Control"),
                                Triple("Overwatch 2", "Dynamic Hero FPS", "Role Queue, Ultimate Economy & Counters"),
                                Triple("League of Legends", "5v5 MOBA Summoner's Rift", "Lanes, Item Scaling & Teamfights"),
                                Triple("Dota 2", "Competitive Strategic MOBA", "Complex Spell Interactions & Carries")
                            )

                            games.forEach { (game, sub, desc) ->
                                val isSelected = game == selectedGame
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = if (isSelected) Color(0x33D0BCFF) else ElegantSurface,
                                    border = androidx.compose.foundation.BorderStroke(
                                        if (isSelected) 1.5.dp else 1.dp,
                                        if (isSelected) ElegantPrimary else ElegantBorder
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            selectedGame = game
                                            val roster = HeroRoster.getHeroesForGame(game)
                                            selectedHeroes = roster.take(2).map { it.name }.toSet()
                                            heroSearchQuery = ""
                                            selectedRoleFilter = "All"
                                        }
                                        .testTag("onboarding_game_$game")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (isSelected) ElegantPrimary else ElegantSurfaceVariant),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.SportsEsports,
                                                contentDescription = null,
                                                tint = if (isSelected) ElegantOnPrimary else TextSecondary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = game,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) ElegantPrimary else TextPrimary
                                            )
                                            Text(
                                                text = sub,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = ElegantSecondary,
                                                fontSize = 11.sp
                                            )
                                            Text(
                                                text = desc,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = TextSecondary,
                                                fontSize = 10.sp
                                            )
                                        }
                                        if (isSelected) {
                                            Box(
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clip(CircleShape)
                                                    .background(ElegantPrimary),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = ElegantOnPrimary,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = { step = 2 },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("onboarding_next_step"),
                                colors = ButtonDefaults.buttonColors(containerColor = ElegantPrimary),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text(
                                    text = "Continue to Pick Signature Heroes",
                                    color = ElegantOnPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = ElegantOnPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    } else {
                        // STEP 2: Select Heroes (Full Roster with Search & Filter)
                        val roster = HeroRoster.getHeroesForGame(selectedGame)
                        val availableRoles = HeroRoster.getAllHeroRolesForGame(selectedGame)

                        val filteredHeroes = roster.filter { hero ->
                            val matchesQuery = heroSearchQuery.isBlank() ||
                                    hero.name.contains(heroSearchQuery, ignoreCase = true) ||
                                    hero.role.contains(heroSearchQuery, ignoreCase = true) ||
                                    hero.signatureAbility.contains(heroSearchQuery, ignoreCase = true)
                            val matchesRole = selectedRoleFilter == "All" ||
                                    hero.role.contains(selectedRoleFilter, ignoreCase = true)
                            matchesQuery && matchesRole
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "STEP 2 OF 2: SIGNATURE MAINS",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ElegantSecondary,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (selectedHeroes.isNotEmpty()) Color(0x33D0BCFF) else ElegantSurfaceVariant,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, if (selectedHeroes.isNotEmpty()) ElegantPrimary else ElegantBorder)
                                ) {
                                    Text(
                                        text = "${selectedHeroes.size} selected",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (selectedHeroes.isNotEmpty()) ElegantPrimary else TextSecondary,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Who do you main in $selectedGame?",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Select any of the ${roster.size} characters. We'll track their meta viability and highlight patch changes for you.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Live Search Bar for all heroes
                            OutlinedTextField(
                                value = heroSearchQuery,
                                onValueChange = { heroSearchQuery = it },
                                placeholder = {
                                    Text(
                                        "Search all ${roster.size} characters or abilities...",
                                        color = TextSecondary.copy(alpha = 0.7f),
                                        fontSize = 13.sp
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search heroes",
                                        tint = ElegantSecondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                trailingIcon = {
                                    if (heroSearchQuery.isNotBlank()) {
                                        IconButton(onClick = { heroSearchQuery = "" }) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = "Clear search",
                                                tint = TextSecondary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("onboarding_hero_search_field"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ElegantPrimary,
                                    unfocusedBorderColor = ElegantBorder,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary,
                                    focusedContainerColor = ElegantSurface,
                                    unfocusedContainerColor = ElegantSurface
                                ),
                                shape = RoundedCornerShape(14.dp)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

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
                                                fontSize = 11.sp,
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
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Results Count & Reset
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (heroSearchQuery.isBlank() && selectedRoleFilter == "All") {
                                        "All ${roster.size} characters"
                                    } else {
                                        "Found ${filteredHeroes.size} of ${roster.size} characters"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )

                                if (heroSearchQuery.isNotBlank() || selectedRoleFilter != "All") {
                                    Text(
                                        text = "Reset filters",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = ElegantPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        modifier = Modifier
                                            .clickable {
                                                heroSearchQuery = ""
                                                selectedRoleFilter = "All"
                                            }
                                            .padding(4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (filteredHeroes.isEmpty()) {
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = ElegantSurface,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "No characters found matching \"$heroSearchQuery\"",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextSecondary
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        OutlinedButton(
                                            onClick = {
                                                heroSearchQuery = ""
                                                selectedRoleFilter = "All"
                                            },
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Text("Show All Characters")
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
                                        val isSelected = selectedHeroes.contains(hero.name)
                                        Surface(
                                            shape = RoundedCornerShape(14.dp),
                                            color = if (isSelected) Color(0x33D0BCFF) else ElegantSurface,
                                            border = androidx.compose.foundation.BorderStroke(
                                                if (isSelected) 1.5.dp else 1.dp,
                                                if (isSelected) ElegantPrimary else ElegantBorder
                                            ),
                                            modifier = Modifier
                                                .clickable {
                                                    selectedHeroes = if (isSelected) {
                                                        selectedHeroes - hero.name
                                                    } else {
                                                        selectedHeroes + hero.name
                                                    }
                                                }
                                                .testTag("onboarding_hero_${hero.name}")
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(24.dp)
                                                        .clip(CircleShape)
                                                        .background(if (isSelected) ElegantPrimary else ElegantSurfaceVariant),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    if (isSelected) {
                                                        Icon(
                                                            imageVector = Icons.Default.Check,
                                                            contentDescription = null,
                                                            tint = ElegantOnPrimary,
                                                            modifier = Modifier.size(14.dp)
                                                        )
                                                    } else {
                                                        Text(
                                                            text = hero.name.take(1),
                                                            color = TextSecondary,
                                                            fontWeight = FontWeight.Bold,
                                                            fontSize = 11.sp
                                                        )
                                                    }
                                                }
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Column {
                                                    Text(
                                                        text = hero.name,
                                                        style = MaterialTheme.typography.labelLarge,
                                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                        color = if (isSelected) TextPrimary else TextPrimary.copy(alpha = 0.85f)
                                                    )
                                                    Text(
                                                        text = "${hero.role} • ${hero.defaultTier}",
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = if (isSelected) ElegantPrimary else TextSecondary,
                                                        fontSize = 10.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { step = 1 },
                                    modifier = Modifier.weight(0.35f),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Back")
                                }

                                Button(
                                    onClick = {
                                        val finalHeroes = if (selectedHeroes.isEmpty()) {
                                            roster.take(2).map { it.name }.toSet()
                                        } else {
                                            selectedHeroes
                                        }
                                        onComplete(selectedGame, finalHeroes)
                                    },
                                    modifier = Modifier
                                        .weight(0.65f)
                                        .testTag("onboarding_complete_button"),
                                    colors = ButtonDefaults.buttonColors(containerColor = ElegantPrimary),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = ElegantOnPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (selectedHeroes.isNotEmpty()) "Confirm ${selectedHeroes.size} Mains" else "Confirm & Launch",
                                        color = ElegantOnPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
