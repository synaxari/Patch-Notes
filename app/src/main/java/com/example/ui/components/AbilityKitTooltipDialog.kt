package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.HeroTuningItem
import com.example.ui.theme.BuffPurple
import com.example.ui.theme.ElegantBorder
import com.example.ui.theme.ElegantBorderLight
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
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Custom canvas artwork illustrating the hero's kit ability (Shield, Speed slide, Void portal,
 * Bladeform, Armor munitions, etc.) so users immediately grasp the reworked mechanic visually.
 */
@Composable
fun AbilityArtworkCanvas(
    iconType: String,
    heroName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF141318))
            .border(1.dp, ElegantBorderLight, RoundedCornerShape(20.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().height(160.dp)) {
            val w = size.width
            val h = size.height
            val cx = w / 2f
            val cy = h / 2f

            when (iconType.uppercase()) {
                "SHIELD" -> {
                    // Futuristic kinetic hexagon shield with deflection ripple waves
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0x5500E5FF), Color(0x227C4DFF), Color.Transparent),
                            center = Offset(cx, cy),
                            radius = w * 0.45f
                        )
                    )

                    // Concentric deflection rings
                    drawCircle(
                        color = Color(0x3300E5FF),
                        radius = 65.dp.toPx(),
                        center = Offset(cx, cy),
                        style = Stroke(width = 2.dp.toPx())
                    )
                    drawCircle(
                        color = Color(0x5500E5FF),
                        radius = 48.dp.toPx(),
                        center = Offset(cx, cy),
                        style = Stroke(width = 3.dp.toPx())
                    )

                    // Hexagon energy barrier
                    val hexPath = Path()
                    val hexRadius = 38.dp.toPx()
                    for (i in 0 until 6) {
                        val angle = (i * 60 - 30) * PI / 180.0
                        val x = cx + hexRadius * cos(angle).toFloat()
                        val y = cy + hexRadius * sin(angle).toFloat()
                        if (i == 0) hexPath.moveTo(x, y) else hexPath.lineTo(x, y)
                    }
                    hexPath.close()

                    drawPath(
                        path = hexPath,
                        brush = Brush.linearGradient(
                            listOf(Color(0xFF00E5FF), Color(0xFF9E86FF)),
                            start = Offset(cx - hexRadius, cy - hexRadius),
                            end = Offset(cx + hexRadius, cy + hexRadius)
                        ),
                        style = Fill
                    )
                    drawPath(
                        path = hexPath,
                        color = Color.White,
                        style = Stroke(width = 2.5.dp.toPx())
                    )

                    // Kinetic deflection spark lines
                    drawLine(
                        color = Color(0xFFE0F7FA),
                        start = Offset(cx - 55.dp.toPx(), cy - 25.dp.toPx()),
                        end = Offset(cx - 38.dp.toPx(), cy - 18.dp.toPx()),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = Color(0xFFE0F7FA),
                        start = Offset(cx + 55.dp.toPx(), cy - 25.dp.toPx()),
                        end = Offset(cx + 38.dp.toPx(), cy - 18.dp.toPx()),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                "SPEED" -> {
                    // Dual lightning slide trails & sprint arrows
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0x66FFEA00), Color(0x3300E5FF), Color.Transparent),
                            center = Offset(cx, cy),
                            radius = w * 0.45f
                        )
                    )

                    // Speed motion streaks
                    for (i in -2..2) {
                        val yOffset = cy + i * 16.dp.toPx()
                        val len = (40 + (2 - kotlin.math.abs(i)) * 25).dp.toPx()
                        drawLine(
                            brush = Brush.horizontalGradient(
                                listOf(Color.Transparent, Color(0xFF00E5FF), Color(0xFFFFEA00)),
                                startX = cx - len,
                                endX = cx + len
                            ),
                            start = Offset(cx - len, yOffset),
                            end = Offset(cx + len, yOffset),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    // Forward sprint chevron
                    val chevPath = Path().apply {
                        moveTo(cx - 20.dp.toPx(), cy - 30.dp.toPx())
                        lineTo(cx + 12.dp.toPx(), cy)
                        lineTo(cx - 20.dp.toPx(), cy + 30.dp.toPx())
                        lineTo(cx - 8.dp.toPx(), cy)
                        close()
                    }
                    drawPath(
                        path = chevPath,
                        brush = Brush.linearGradient(listOf(Color(0xFFFFEA00), Color(0xFF00E5FF))),
                        style = Fill
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 4.dp.toPx(),
                        center = Offset(cx + 15.dp.toPx(), cy)
                    )
                }

                "PORTAL" -> {
                    // Dimensional void rift piercing through solid geometry
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0x777C4DFF), Color(0x33B388FF), Color.Transparent),
                            center = Offset(cx, cy),
                            radius = w * 0.5f
                        )
                    )

                    // Solid wall barrier representation
                    drawRoundRect(
                        color = Color(0x44332D41),
                        topLeft = Offset(cx - 60.dp.toPx(), cy - 45.dp.toPx()),
                        size = Size(120.dp.toPx(), 90.dp.toPx()),
                        cornerRadius = CornerRadius(12.dp.toPx())
                    )

                    // Piercing void corridor
                    drawOval(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFE1BEE7), Color(0xFF7C4DFF), Color(0xFF311B92)),
                            center = Offset(cx, cy)
                        ),
                        topLeft = Offset(cx - 28.dp.toPx(), cy - 42.dp.toPx()),
                        size = Size(56.dp.toPx(), 84.dp.toPx())
                    )
                    drawOval(
                        color = Color.White,
                        topLeft = Offset(cx - 28.dp.toPx(), cy - 42.dp.toPx()),
                        size = Size(56.dp.toPx(), 84.dp.toPx()),
                        style = Stroke(width = 2.5.dp.toPx())
                    )
                }

                "SWORD" -> {
                    // Dual spinning energy blades with radiant duelist aura
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0x66FFB300), Color(0x33FF6F00), Color.Transparent),
                            center = Offset(cx, cy),
                            radius = w * 0.45f
                        )
                    )

                    // Slashing arc lines
                    drawArc(
                        brush = Brush.sweepGradient(listOf(Color(0xFFFFB300), Color(0xFFFFE082), Color.Transparent)),
                        startAngle = 45f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset(cx - 45.dp.toPx(), cy - 45.dp.toPx()),
                        size = Size(90.dp.toPx(), 90.dp.toPx()),
                        style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                    )
                    drawArc(
                        brush = Brush.sweepGradient(listOf(Color(0xFFFF6F00), Color(0xFFFFB300), Color.Transparent)),
                        startAngle = 225f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset(cx - 45.dp.toPx(), cy - 45.dp.toPx()),
                        size = Size(90.dp.toPx(), 90.dp.toPx()),
                        style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // Blade edge
                    drawLine(
                        color = Color.White,
                        start = Offset(cx - 30.dp.toPx(), cy + 30.dp.toPx()),
                        end = Offset(cx + 30.dp.toPx(), cy - 30.dp.toPx()),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                else -> {
                    // Armor piercing rounds & munitions
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0x55FF5252), Color(0x33FF9800), Color.Transparent),
                            center = Offset(cx, cy),
                            radius = w * 0.45f
                        )
                    )

                    // Crosshair / tactical targeting ring
                    drawCircle(
                        color = Color(0x44FF5252),
                        radius = 42.dp.toPx(),
                        center = Offset(cx, cy),
                        style = Stroke(width = 2.dp.toPx())
                    )
                    drawLine(
                        color = Color(0xFFFF5252),
                        start = Offset(cx - 50.dp.toPx(), cy),
                        end = Offset(cx + 50.dp.toPx(), cy),
                        strokeWidth = 2.dp.toPx()
                    )
                    drawLine(
                        color = Color(0xFFFF5252),
                        start = Offset(cx, cy - 50.dp.toPx()),
                        end = Offset(cx, cy + 50.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 6.dp.toPx(),
                        center = Offset(cx, cy)
                    )
                }
            }
        }

        // Overlay Hero & Ability Kit Label
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0xEE1C1B1F))
                    )
                )
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = heroName.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = ElegantSecondary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "HERO SIGNATURE KIT ABILITY",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    fontSize = 10.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = ElegantPrimary
            ) {
                Text(
                    text = "OFFICIAL REWORK",
                    color = ElegantOnPrimary,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
            }
        }
    }
}

/**
 * Interactive dialog that pops up when a user clicks an ability rework to inspect
 * before vs. after rework mechanics and view tactical toolkit details.
 */
@Composable
fun AbilityKitTooltipDialog(
    hero: HeroTuningItem,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("ability_tooltip_dialog_${hero.name}"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = ElegantSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, ElegantBorderLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Header with Hero Name, Ability Key & Close Button
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
                                .background(ElegantPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (hero.abilityIconType.uppercase()) {
                                    "SHIELD" -> Icons.Default.Shield
                                    "SPEED" -> Icons.Default.Speed
                                    "PORTAL" -> Icons.Default.Bolt
                                    "SWORD" -> Icons.Default.AutoAwesome
                                    else -> Icons.Default.ElectricBolt
                                },
                                contentDescription = null,
                                tint = ElegantOnPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = hero.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "${hero.roleOrLane} • ${hero.abilityKey}",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElegantSecondary
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(ElegantSurfaceVariant)
                            .testTag("close_ability_tooltip_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Tooltip",
                            tint = TextPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Hero Kit Visual Artwork Canvas
                AbilityArtworkCanvas(
                    iconType = hero.abilityIconType,
                    heroName = hero.name,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Ability Name Display
                Text(
                    text = hero.abilityName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
                Text(
                    text = "Key Binding / Slot: ${hero.abilityKey}",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElegantPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Comparative Kit Rework Breakdown: BEFORE vs. AFTER
                Text(
                    text = "MECHANICAL REWORK COMPARISON",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElegantSecondary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Before Rework (Legacy Kit)
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0x22F2B8B5)),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x44F2B8B5)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.HighlightOff,
                                contentDescription = null,
                                tint = NerfRed,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "BEFORE REWORK (Legacy Mechanics)",
                                style = MaterialTheme.typography.labelSmall,
                                color = NerfRed,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = hero.beforeRework,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimary,
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // After Rework (Current Meta)
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0x22D0BCFF)),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x55D0BCFF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = ElegantPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "AFTER REWORK (Current Patch Live)",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElegantPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = hero.afterRework,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Tactical Matchup & Counterplay Section
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = ElegantSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = ElegantSecondary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "TACTICAL EXECUTION & COUNTERPLAY",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElegantSecondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = hero.mechanicBreakdown,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Win-Rate Delta & Viability Forecast Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = ElegantPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Win-Rate Impact:",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                    Text(
                        text = hero.winRateForecastDelta,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (hero.winRateForecastDelta.startsWith("+")) ElegantPrimary else NerfRed
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Dismiss Button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("dismiss_ability_tooltip_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = ElegantPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Understood",
                        color = ElegantOnPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
