package com.example.data.model

import com.squareup.moshi.JsonClass

enum class GameGenre(val displayName: String, val iconLabel: String) {
    SHOOTER("Shooters", "Crosshair"),
    MOBA("MOBAs", "Hexagon"),
    BATTLE_ROYALE("Battle Royale", "Shield"),
    HYBRID("Hybrid / Hero Shooter", "Zap")
}

enum class ChangeType(val label: String, val badgeColorHex: Long) {
    BUFF("BUFF", 0xFF10B981),
    NERF("NERF", 0xFFEF4444),
    REWORK("REWORK", 0xFF8B5CF6),
    ADJUSTMENT("ADJUSTED", 0xFFF59E0B)
}

enum class PacingImpact(val label: String, val description: String) {
    FASTER_TEMPO("Accelerated Pace", "Early aggression and fast snowballs rewarded"),
    NEUTRAL("Stable Macro", "Pacing remains consistent with current meta"),
    SLOWER_TEMPO("Extended Scaling", "Late-game compositions and scaling prioritized")
}

@JsonClass(generateAdapter = true)
data class HeroTuningItem(
    val name: String,
    val roleOrLane: String,
    val changeType: String, // BUFF, NERF, REWORK, ADJUSTMENT
    val abilityImpact: String,
    val draftSynergy: String,
    val competitiveViability: String, // e.g. "Tier S", "Tier A+", "Situational Counter"
    val winRateForecastDelta: String, // e.g. "+3.4%", "-2.1%"
    val abilityName: String = "",
    val abilityKey: String = "",
    val abilityIconType: String = "SHIELD", // SHIELD, SPEED, PORTAL, SWORD, BULLET, FIRE, EXPLOSIVE, MAGIC
    val beforeRework: String = "",
    val afterRework: String = "",
    val mechanicBreakdown: String = ""
)

data class PlayerTimelineCatchup(
    val gameTitle: String,
    val yearStopped: Int,
    val monthStopped: Int?, // 1 to 12 or null
    val earliestBaselinePatch: String,
    val currentLatestPatch: String,
    val estimatedPatchesBehind: Int,
    val majorReworkHighlights: List<String>,
    val strategicCatchupBrief: String,
    val rawComparisonPatchNotes: String
)

@JsonClass(generateAdapter = true)
data class ArsenalItem(
    val name: String,
    val category: String, // WEAPON, MOBA_ITEM, ECONOMY
    val ttkOrCostImpact: String, // e.g. "TTK: 520ms -> 480ms (-40ms)" or "Cost: 3000g -> 2800g (-200g)"
    val buildPathOrPowerSpikes: String,
    val competitiveImpact: String
)

@JsonClass(generateAdapter = true)
data class MapObjectiveItem(
    val name: String,
    val timingChanges: String, // e.g. "Spawn moved from 20:00 to 18:30"
    val routingOrGeometryImpact: String,
    val macroPacingShift: String
)

@JsonClass(generateAdapter = true)
data class PatchAnalysisReport(
    val id: String,
    val gameTitle: String,
    val genre: String, // SHOOTER or MOBA or HYBRID
    val patchVersion: String,
    val summaryHeadline: String,
    val metaShiftScore: Int, // 1 to 100
    val pacingImpact: String, // FASTER_TEMPO, NEUTRAL, SLOWER_TEMPO
    val characterTuning: List<HeroTuningItem> = emptyList(),
    val arsenalEconomy: List<ArsenalItem> = emptyList(),
    val mapObjectives: List<MapObjectiveItem> = emptyList(),
    val macroStrategySummary: List<String> = emptyList(),
    val keyWinners: List<String> = emptyList(),
    val keyLosers: List<String> = emptyList(),
    val rawMarkdownReport: String,
    val timestamp: Long = System.currentTimeMillis()
)
