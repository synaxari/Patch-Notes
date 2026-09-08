package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HeroMetaProfile
import com.example.data.model.HeroPatchStatus
import com.example.data.model.HeroRoster
import com.example.data.model.HeroTuningItem
import com.example.data.model.PatchAnalysisReport
import com.example.ui.theme.BuffGreen
import com.example.ui.theme.ElegantBorder
import com.example.ui.theme.ElegantBorderLight
import com.example.ui.theme.ElegantDarkBg
import com.example.ui.theme.ElegantPrimary
import com.example.ui.theme.ElegantSecondary
import com.example.ui.theme.ElegantSurface
import com.example.ui.theme.ElegantSurfaceVariant
import com.example.ui.theme.NerfRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@Composable
fun FavoriteHeroesStatusCard(
    gameTitle: String,
    favoriteHeroNames: Set<String>,
    currentReport: PatchAnalysisReport?,
    onManageFavoritesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inspectingHeroTuning by remember { mutableStateOf<HeroTuningItem?>(null) }

    if (inspectingHeroTuning != null) {
        AbilityKitTooltipDialog(
            hero = inspectingHeroTuning!!,
            onDismiss = { inspectingHeroTuning = null }
        )
    }

    val roster = HeroRoster.getHeroesForGame(gameTitle)
    val favoriteProfiles = roster
        .filter { favoriteHeroNames.contains(it.name) }
        .map { HeroRoster.evaluateHeroMeta(it, currentReport) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("favorite_heroes_status_card"),
        colors = CardDefaults.cardColors(containerColor = ElegantSurface),
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Card Header
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
                            .background(Color(0xFFFFD54F).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "MY SIGNATURE HEROES",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElegantPrimary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                        Text(
                            text = "$gameTitle Meta Standing",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }

                // Manage Favorites Button
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = ElegantSurfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder),
                    modifier = Modifier
                        .clickable { onManageFavoritesClick() }
                        .testTag("manage_favorites_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Favorites",
                            tint = ElegantSecondary,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (favoriteHeroNames.isEmpty()) "Select Heroes" else "Edit (${favoriteProfiles.size})",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElegantSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (favoriteProfiles.isEmpty()) {
                // Empty State Prompt
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF141318),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No favorite heroes tracked for $gameTitle yet!",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tap 'Select Heroes' to track your mains, see their meta tier standing, and monitor if this patch buffed, nerfed, or reworked them.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }
            } else {
                // Horizontal / Carousel Cards of Favorite Heroes
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    favoriteProfiles.forEach { profile ->
                        FavoriteHeroBadgeItem(
                            profile = profile,
                            onInspectTooltip = {
                                profile.tuningItem?.let { inspectingHeroTuning = it }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteHeroBadgeItem(
    profile: HeroMetaProfile,
    onInspectTooltip: () -> Unit
) {
    val (statusBg, statusBorder, statusText, statusColor) = when (profile.patchStatus) {
        HeroPatchStatus.REWORKED -> HeroBadgeColors(Color(0x33D0BCFF), ElegantPrimary, "REWORKED", ElegantPrimary)
        HeroPatchStatus.BUFFED -> HeroBadgeColors(Color(0x3385DFBA), BuffGreen, "BUFFED", BuffGreen)
        HeroPatchStatus.NERFED -> HeroBadgeColors(Color(0x33F2B8B5), NerfRed, "NERFED", NerfRed)
        HeroPatchStatus.ADJUSTED -> HeroBadgeColors(Color(0x33CCC2DC), ElegantSecondary, "ADJUSTED", ElegantSecondary)
        HeroPatchStatus.STABLE -> HeroBadgeColors(Color(0x22938F99), ElegantBorder, "STABLE META", TextSecondary)
    }

    val isPositiveWr = profile.winRateDelta.startsWith("+")

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFF151419),
        border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder),
        modifier = Modifier
            .width(260.dp)
            .testTag("fav_hero_item_${profile.hero.name}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Avatar, Name, Role, Tier
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(ElegantPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = profile.hero.name.take(1),
                            color = Color(0xFF381E72),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = profile.hero.name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = profile.hero.role,
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                    }
                }

                // Tier Chip
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ElegantSurfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder)
                ) {
                    Text(
                        text = profile.currentViabilityTier,
                        style = MaterialTheme.typography.labelSmall,
                        color = ElegantPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Patch Status Banner
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = statusBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, statusBorder)
                ) {
                    Text(
                        text = statusText,
                        color = statusColor,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                // Win rate delta
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (profile.winRateDelta.startsWith("+") || profile.winRateDelta.startsWith("-")) {
                        Icon(
                            imageVector = if (isPositiveWr) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                            contentDescription = null,
                            tint = if (isPositiveWr) BuffGreen else NerfRed,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                    Text(
                        text = profile.winRateDelta,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isPositiveWr) BuffGreen else if (profile.winRateDelta.startsWith("-")) NerfRed else TextSecondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Status Headline
            Text(
                text = profile.statusHeadline,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                maxLines = 3
            )

            // If hero has rework/tuning item, provide Inspect Tooltip button
            if (profile.tuningItem != null && profile.tuningItem.abilityName.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = ElegantSurfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onInspectTooltip() }
                        .testTag("fav_hero_inspect_${profile.hero.name}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null,
                            tint = ElegantPrimary,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Inspect Rework Tooltip",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElegantPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

private data class HeroBadgeColors(
    val bg: Color,
    val border: Color,
    val text: String,
    val textColor: Color
)
private val ElegantBorderSubtle = Color(0x3349454F)
