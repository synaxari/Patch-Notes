package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.data.model.PatchAnalysisReport
import com.example.ui.components.ArsenalTtkCard
import com.example.ui.components.FavoriteHeroesStatusCard
import com.example.ui.components.HeroTuningCard
import com.example.ui.components.MapObjectiveCard
import com.example.ui.components.MetaScoreGauge
import com.example.ui.theme.BuffPurple
import com.example.ui.theme.ElegantBorder
import com.example.ui.theme.ElegantBorderLight
import com.example.ui.theme.ElegantBorderSubtle
import com.example.ui.theme.ElegantDarkBg
import com.example.ui.theme.ElegantOnPrimary
import com.example.ui.theme.ElegantPrimary
import com.example.ui.theme.ElegantSecondary
import com.example.ui.theme.ElegantSurface
import com.example.ui.theme.ElegantSurfaceVariant
import com.example.ui.theme.NerfRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@Composable
fun AnalysisResultScreen(
    report: PatchAnalysisReport,
    isSaved: Boolean,
    activeSubTab: Int,
    favoriteHeroes: Set<String> = emptySet(),
    onSubTabChanged: (Int) -> Unit,
    onToggleSave: () -> Unit,
    onBackToInput: () -> Unit,
    onManageFavorites: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ElegantDarkBg)
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ElegantDarkBg)
                .border(
                    width = 1.dp,
                    color = ElegantBorder,
                    shape = RoundedCornerShape(0.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onBackToInput,
                    modifier = Modifier.testTag("back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = "META ANALYSIS",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElegantPrimary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "${report.gameTitle} ${report.patchVersion}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }

            Row {
                IconButton(
                    onClick = onToggleSave,
                    modifier = Modifier.testTag("save_report_button")
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save Report",
                        tint = if (isSaved) ElegantPrimary else TextSecondary
                    )
                }

                IconButton(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_SUBJECT,
                                "Meta Shift Analysis: ${report.gameTitle} ${report.patchVersion}"
                            )
                            putExtra(Intent.EXTRA_TEXT, report.rawMarkdownReport)
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Meta Shift Report"))
                    },
                    modifier = Modifier.testTag("share_report_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = ElegantSecondary
                    )
                }
            }
        }

        // Sub-tabs row
        val tabs = listOf(
            Triple(0, "Legend Tuning (${report.characterTuning.size})", Icons.Default.SportsEsports),
            Triple(1, "Arsenal (${report.arsenalEconomy.size})", Icons.Default.Speed),
            Triple(2, "Macro & Map (${report.mapObjectives.size})", Icons.Default.Map),
            Triple(3, "Analyst Verdict", Icons.Default.Description)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ElegantDarkBg)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tabs.forEach { (index, title, icon) ->
                val isSelected = activeSubTab == index
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) ElegantPrimary else ElegantSurface,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) ElegantPrimary else ElegantBorder
                    ),
                    modifier = Modifier
                        .clickable { onSubTabChanged(index) }
                        .testTag("result_subtab_$index")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isSelected) ElegantOnPrimary else ElegantSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) ElegantOnPrimary else TextPrimary
                        )
                    }
                }
            }
        }

        // Main Tab Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(6.dp))
                // Meta Disruption Meter
                MetaScoreGauge(
                    score = report.metaShiftScore,
                    pacingText = report.pacingImpact
                )
            }

            item {
                // Winners & Losers Quick Highlights
                Card(
                    colors = CardDefaults.cardColors(containerColor = ElegantSurface),
                    shape = RoundedCornerShape(24.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "ESTIMATED WIN-RATE Δ HIGHLIGHTS",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElegantPrimary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Winners
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x33D0BCFF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ThumbUp,
                                    contentDescription = null,
                                    tint = ElegantPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Buffed / Rising: ",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElegantPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = report.keyWinners.joinToString(", "),
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Losers
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x33F2B8B5)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ThumbDown,
                                    contentDescription = null,
                                    tint = NerfRed,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Nerfed / Falling: ",
                                style = MaterialTheme.typography.labelSmall,
                                color = NerfRed,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = report.keyLosers.joinToString(", "),
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }

            when (activeSubTab) {
                0 -> {
                    // Prominent Signature Heroes Meta Status
                    item {
                        FavoriteHeroesStatusCard(
                            gameTitle = report.gameTitle,
                            favoriteHeroNames = favoriteHeroes,
                            currentReport = report,
                            onManageFavoritesClick = onManageFavorites
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Character / Legend Tuning
                    if (report.characterTuning.isEmpty()) {
                        item {
                            EmptySectionNotice("No character/legend tuning adjustments detected in this patch.")
                        }
                    } else {
                        items(report.characterTuning) { hero ->
                            HeroTuningCard(item = hero)
                        }
                    }
                }

                1 -> {
                    // Arsenal & TTK / Item Economy
                    if (report.arsenalEconomy.isEmpty()) {
                        item {
                            EmptySectionNotice("No weapon TTK or item economy changes detected in this patch.")
                        }
                    } else {
                        items(report.arsenalEconomy) { weapon ->
                            ArsenalTtkCard(item = weapon)
                        }
                    }
                }

                2 -> {
                    // Map & Objectives
                    if (report.mapObjectives.isEmpty()) {
                        item {
                            EmptySectionNotice("No map geometry or objective timing modifications detected.")
                        }
                    } else {
                        items(report.mapObjectives) { mapItem ->
                            MapObjectiveCard(item = mapItem)
                        }
                    }
                }

                3 -> {
                    // Strategic Summary & Full Markdown
                    item {
                        StrategicSummaryCard(report = report)
                    }
                }
            }

            // Quick Copy & Export Action Bar
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                            val clip = ClipData.newPlainText("Meta Shift Report", report.rawMarkdownReport)
                            clipboard?.setPrimaryClip(clip)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("copy_report_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElegantSurface,
                            contentColor = ElegantPrimary
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Copy Brief",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = onBackToInput,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("analyze_new_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElegantPrimary,
                            contentColor = ElegantOnPrimary
                        ),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text(
                            text = "Analyze Another",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun StrategicSummaryCard(report: PatchAnalysisReport) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Analyst Verdict Quote Card (as in HTML mockup)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = ElegantSurfaceVariant),
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x40D0BCFF))
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "ANALYST VERDICT",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElegantPrimary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                report.macroStrategySummary.forEach { bullet ->
                    Text(
                        text = "“$bullet”",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        lineHeight = 22.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }

        // Formatted Markdown Brief Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = ElegantSurface),
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "TACTICAL BREAKDOWN",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElegantSecondary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = ElegantDarkBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = report.rawMarkdownReport,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        lineHeight = 19.sp,
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun EmptySectionNotice(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ElegantSurface),
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorder)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = TextTertiary
            )
        }
    }
}
