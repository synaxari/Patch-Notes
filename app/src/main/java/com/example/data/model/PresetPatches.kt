package com.example.data.model

data class PresetPatchNote(
    val id: String,
    val gameTitle: String,
    val genre: GameGenre,
    val patchVersion: String,
    val title: String,
    val shortSummary: String,
    val rawPatchNotes: String,
    val releaseYear: Int = 2024,
    val releaseMonth: Int? = 6, // 1 to 12, null if unspecified
    val baselinePreviousVersion: String = "Patch 8.00",
    val returningPlayerDeltaSummary: String = "Major duelist mobility & shield mechanics overhaul since early 2024."
)

object PresetPatches {
    val allPresets = listOf(
        PresetPatchNote(
            id = "val_8_11",
            gameTitle = "Valorant",
            genre = GameGenre.SHOOTER,
            patchVersion = "Patch 8.11",
            title = "Duelist Rework & Haven Map Return",
            shortSummary = "Iso Double Tap shield rework, Reyna speed buff, Neon slide pinpoint accuracy.",
            releaseYear = 2024,
            releaseMonth = 6,
            baselinePreviousVersion = "Patch 8.00 (Jan 2024)",
            returningPlayerDeltaSummary = "Since Episode 8 start: Iso gained instant reactive shield on E without needing kill orbs. Neon slides have zero mid-air firing penalty. Raze double satchel speed dampened.",
            rawPatchNotes = """
VALORANT PATCH NOTES 8.11 - DUELIST REWORK & MAP POOL UPDATE

AGENT UPDATES:
ISO:
- Double Tap (E): Iso now grants himself an invulnerable Shield after an instant 1s animation. No longer requires shooting an energy orb to activate shield. Scoring 2 kills refreshes duration.
- Undercut (Q): Fragile duration increased from 4s to 5.2s.

REYNA:
- Dismiss (E): Movement speed bonus increased from 20% to 35%. Duration reduced from 2.0s to 1.5s.
- Devour (Q): Healing reduced from 100 to 50 HP. Overheal duration changed to infinite (no decay).
- Empress (X): No longer has a timer limit; lasts until Reyna is eliminated or round ends.

NEON:
- High Gear (E): Neon can now slide twice per activation. Slide weapon accuracy error removed—weapons are now 100% accurate mid-slide.
- Fast Lane (C): Wall dissipation delay removed; dissolves immediately when expired.

RAZE:
- Blast Pack (Q): Satchel horizontal velocity reduced by 18%. Exploding second satchel mid-air now incurs a 0.25s velocity dampening decay.

ARSENAL & ECONOMY:
- Outlaw (Sniper): Body shot damage reduced from 140 to 135. Armor penetration unaffected. Reload time increased from 3.8s to 4.2s.
- Sheriff: Headshot falloff damage past 30m increased to 145 (no longer 1-taps full armor beyond 30m).

MAP POOL & OBJECTIVES:
- HAVEN returns to competitive queue; BREEZE and SPLIT removed.
- Haven A-Long geometry adjusted: Radianite crates shifted 1.5 meters deeper into long, narrowing peek angles for defenders.
- Spike Defuse Tap Sound: Auditory range increased by 2.5 meters.
            """.trimIndent()
        ),
        PresetPatchNote(
            id = "lol_14_10",
            gameTitle = "League of Legends",
            genre = GameGenre.MOBA,
            patchVersion = "Patch 14.10",
            title = "ADC Itemization & Lethality Overhaul",
            shortSummary = "Infinity Edge 80 AD, Zephyr returns, Kraken Slayer rework, Baron pit geometry.",
            releaseYear = 2024,
            releaseMonth = 5,
            baselinePreviousVersion = "Patch 14.1 (Jan 2024)",
            returningPlayerDeltaSummary = "Since Season 14 Start: Mythic items were permanently removed. Corki lost The Package and returned to Bot Lane. Infinity Edge buffed to 80 AD with 50% crit bonus.",
            rawPatchNotes = """
LEAGUE OF LEGENDS PATCH 14.10 - CARRY ITEMIZATION & MACRO PACING

CHAMPION TUNING:
CORKI:
- Passive (The Package): Removed entirely.
- Hextech Munitions: Basic attacks now deal 100% Physical Damage (converted from magic).
- Gatling Gun (E): Armor and Magic Resist shred increased to 16/20/24/28/32 flat reduction.

SAMIRA:
- Inferno Trigger (R): Base damage increased by 10 per rank. Life steal ratio increased to 80%.

ARSENAL & ITEM ECONOMY:
INFINITY EDGE:
- Total Cost: 3400g (unchanged). Attack Damage increased from 65 to 80. Critical Strike Damage bonus increased to 50%.
KRAKEN SLAYER:
- Total Cost: 3100g. Recipe changed to Rectrix + Hearthbound Axe + Recurve Bow. No longer grants Critical Strike Chance; grants 50 AD, 40% AS, 7% Movement Speed. Consecutive hits deal ramping missing-health physical damage.
ZEPHYR:
- Upgrade from Berserker's Greaves at Level 15 (Cost: 2000g). Grants 45% AS, 45 Flat Move Speed, and Ghosting on attack.

MAP & OBJECTIVES:
- Baron Nashor: Pit opening geometry adjusted—terrain choke widened by 120 units on river entrance.
- Voidgrubs: Initial spawn time delayed from 5:00 to 6:00. Spawn count remains 3. Buff duration per grub reduced by 15%.
            """.trimIndent()
        ),
        PresetPatchNote(
            id = "apex_s21",
            gameTitle = "Apex Legends",
            genre = GameGenre.BATTLE_ROYALE,
            patchVersion = "Season 21: Upheaval",
            title = "Alter Debut & Havoc Nerfs",
            shortSummary = "Alter Void Passage, Havoc hipfire nerf, Broken Moon map overhaul.",
            releaseYear = 2024,
            releaseMonth = 5,
            baselinePreviousVersion = "Season 20: Breakout (Feb 2024)",
            returningPlayerDeltaSummary = "Since S20: Armor core leveling replaced ground shields. Alter introduced 3D wall-phasing portals (Void Passage). Havoc hipfire nerfed by 22%.",
            rawPatchNotes = """
APEX LEGENDS - SEASON 21 UPHEAVAL PATCH NOTES

LEGEND TUNING:
ALTER:
- Void Passage (Tactical): Creates a portal passageway through solid geometry surfaces (depth up to 20m). Cooldown 30s.
- Void Nexus (Ultimate): Deploys a regroup beacon allowing downed or active squadmates to phase back from anywhere on the map within 200m.

BLOODHOUND:
- Beast of the Hunt (Ultimate): Threat vision no longer sees through Bangalore smoke screens. Duration reduced to 25s.

ARSENAL & TTK:
HAVOC RIFLE:
- Hipfire spread increased by 22%. Turbocharger hop-up drop rate reduced by 35%.
- Damage per bullet reduced from 18 to 17 (increases close-range TTK by ~60ms against purple shields).

WINGMAN:
- Moves from Care Package back to ground loot. Uses Sniper Ammo and Sniper Mag. Skullpiercer compatibility restored.

MAP & OBJECTIVES:
- BROKEN MOON OVERHAUL: Promenade POI completely demolished and replaced by Quarantine Zone with high-density vertical cover.
- Ring 1 Closing: Delay before Ring 1 starts closing reduced from 90s to 75s, speeding up early macro rotations.
            """.trimIndent()
        ),
        PresetPatchNote(
            id = "dota_7_36",
            gameTitle = "Dota 2",
            genre = GameGenre.MOBA,
            patchVersion = "Patch 7.36",
            title = "Innate Abilities & Hero Facets",
            shortSummary = "Innates for all 124 heroes, Facet customization, Roshan banner buff.",
            releaseYear = 2024,
            releaseMonth = 5,
            baselinePreviousVersion = "Patch 7.35 (Dec 2023)",
            returningPlayerDeltaSummary = "Since 7.35: Every single hero in Dota 2 received an Innate Ability active from level 1, plus 2-3 selectable Facets in pre-game draft.",
            rawPatchNotes = """
DOTA 2 PATCH 7.36 - INNATE ABILITIES & HERO FACETS

HERO TUNING & FACETS:
JUGGERNAUT:
- Innate (Duelist): Deals 10% bonus damage when facing enemy hero targets.
- Facet 1 (Bladeform): Grants stacking Agility while not taking damage.
- Facet 2 (Spin to Win): Blade Fury movement speed bonus increased by 10% and deals 15% bonus tick damage.

TINKER:
- Rework: Rearm now only refreshes basic abilities (no longer refreshes items like Blink Dagger or BKB).
- Defense Matrix granted as Innate Ability with 100-400 physical barrier.

ARSENAL & ITEM ECONOMY:
BLOODTHORN:
- Soul Rend bonus magic damage per hit reduced from 60 to 50. Recipe cost increased by 250g (delayed timing).
ETERNAL SHROUD:
- Mana conversion from spell damage capped at 300 mana per instance.

MAP & MACRO PACING:
- Roshan Pit: Roshan Banner duration increased from 5 to 8 minutes. Teleport channel time on Twin Gates reduced by 0.5s.
- Tormentor: Initial spawn barrier strength increased by 15%, requiring full team commitment.
            """.trimIndent()
        ),
        PresetPatchNote(
            id = "mlbb_1_8",
            gameTitle = "Mobile Legends",
            genre = GameGenre.MOBA,
            patchVersion = "Patch 1.8.78",
            title = "Marksman Range & Lord Defense",
            shortSummary = "Moskov attack speed rework, Malefic Gun debut, Enhanced Lord damage boost.",
            releaseYear = 2024,
            releaseMonth = 4,
            baselinePreviousVersion = "Patch 1.8.44 (Jan 2024)",
            returningPlayerDeltaSummary = "Since early 2024: Marksman attack range extended with Malefic Gun; Moskov basic attacks now pierce 100% through secondary targets.",
            rawPatchNotes = """
MOBILE LEGENDS: BANG BANG - PATCH 1.8.78

HERO TUNING:
MOSKOV:
- Abyss Walker: Attack speed bonus increased to 1.4x scaling. Basic attack penetration deals 100% damage to targets behind.
- Spear of Destruction: Pin duration on wall collision increased to 1.8s.

LING:
- Energy recovery while on walls increased by 25%. Cooldown of Tempest of Blades reduced by 6s at rank 1.

ARSENAL & EQUIPMENT:
MALEFIC GUN:
- New Marksman Item (+45 Physical Attack, +20% AS, +15% Lifesteal). Passive: Basic attacks grant +15% attack range for 3s (Cooldown: 6s).
CORROSION SCYTHE:
- Slow effect per stack reduced from 8% to 6%.

OBJECTIVES & TURRETS:
- Evolved Lord (18:00+): Charge damage against base turrets increased by 20%. Wave clear time required by defenders increased.
            """.trimIndent()
        ),
        PresetPatchNote(
            id = "warzone_s4",
            gameTitle = "Warzone",
            genre = GameGenre.SHOOTER,
            patchVersion = "Season 4 Reloaded",
            title = "Kar98k TTK & Gulag Overhaul",
            shortSummary = "Kar98k damage profile adjusted, Superi 46 movement speed nerf, Urzikstan bunker loot.",
            releaseYear = 2024,
            releaseMonth = 6,
            baselinePreviousVersion = "Season 3 (April 2024)",
            returningPlayerDeltaSummary = "Since Season 3: Kar98k re-introduced with 1-shot headshot lethality under 40m. Superi 46 SMG sprint-to-fire slowed by 18ms. Standardized 3-lane gulag arena.",
            rawPatchNotes = """
CALL OF DUTY: WARZONE - SEASON 4 RELOADED PATCH NOTES

WEAPONS & TTK DYNAMICS:
KAR98K (Marksman Rifle):
- Upper Torso Damage: Reduced from 120 to 109. Max damage range reduced from 55m to 42m.
- Headshot multiplier within 40m remains a 1-shot down against 3-plate armor.

SUPERI 46 (SMG):
- Strafe speed reduced by 12%. Sprint-to-fire time increased from 110ms to 128ms.
- Close-range TTK increased from 520ms to 585ms.

MAP & OBJECTIVES:
- Urzikstan Bunker Access: Keycard puzzle doors unlocked, spawning guaranteed Specialist Perks and UAV killstreaks.
- Gulag: Removed night-vision gulag variant; standardized 3-lane symmetrical high-visibility arena.
            """.trimIndent()
        )
    )
}

object PatchTimelineEngine {
    val supportedYears = listOf(2023, 2024, 2025, 2026)
    val months = listOf(
        1 to "January", 2 to "February", 3 to "March", 4 to "April",
        5 to "May", 6 to "June", 7 to "July", 8 to "August",
        9 to "September", 10 to "October", 11 to "November", 12 to "December"
    )

    fun calculateCatchup(
        gameTitle: String,
        yearStopped: Int,
        monthStopped: Int?
    ): PlayerTimelineCatchup {
        val title = gameTitle.trim()
        val monthName = monthStopped?.let { m -> months.find { it.first == m }?.second } ?: "Any Month"
        val timeLabel = if (monthStopped != null) "$monthName $yearStopped" else "$yearStopped"

        val normalized = title.lowercase()
        return when {
            normalized.contains("valorant") -> {
                val baseline = when {
                    yearStopped <= 2023 -> "Patch 6.08 (Lotus / Gekko debut)"
                    yearStopped == 2024 && (monthStopped == null || monthStopped <= 3) -> "Patch 8.00 (Episode 8 Act 1 - Outlaw release)"
                    yearStopped == 2024 && monthStopped in 4..6 -> "Patch 8.07 (Clove debut & Omen tweaks)"
                    else -> "Patch 8.09 (Breeze removal announcement)"
                }
                val patchesBehind = if (yearStopped <= 2023) 18 else if (yearStopped == 2024 && (monthStopped ?: 1) <= 3) 11 else 5
                PlayerTimelineCatchup(
                    gameTitle = "Valorant",
                    yearStopped = yearStopped,
                    monthStopped = monthStopped,
                    earliestBaselinePatch = baseline,
                    currentLatestPatch = "Patch 8.11 (Duelist Rework)",
                    estimatedPatchesBehind = patchesBehind,
                    majorReworkHighlights = listOf(
                        "Iso Double Tap (E): Shield activation made instant without requiring kill-orb shooting.",
                        "Neon High Gear (E): Dual slides added; 100% weapon firing accuracy mid-slide.",
                        "Reyna Dismiss/Devour: Overheal decay removed (infinite duration); Empress has no timer.",
                        "Outlaw Sniper: New 2-bullet sniper weapon added; 140 body damage dynamic.",
                        "Map Pool: Haven returned; Breeze & Split rotated out of competitive pool."
                    ),
                    strategicCatchupBrief = "Since you stopped playing in $timeLabel, Riot shifted the entry-duelist meta toward aggressive proactive initiation. Iso and Neon dominate close-range duels with instant defenses and sliding precision, replacing static flash-and-peek play.",
                    rawComparisonPatchNotes = """
VALORANT RETURNING PLAYER CATCH-UP (Baseline: $baseline -> Latest: Patch 8.11)

MAJOR HERO & AGENT REWORKS:
- ISO: Double Tap reworked. Grants 1-hit invulnerable shield on cast after 1s windup. No longer requires shooting an orb after getting a kill.
- NEON: High Gear reworked with 2 slides and 0% slide bullet spread penalty.
- REYNA: Devour overheal is now permanent for the round; Empress timer removed.
- RAZE: Satchel horizontal jump speed nerfed by 18%.

NEW ARSENAL & WEAPON DYNAMICS:
- Outlaw (Semi-Auto Sniper): Added to shop (2400 creds). Fires two high-caliber rounds dealing 135 body damage, instantly killing half-armored opponents.
- Sheriff: Headshot falloff increased beyond 30m; full-armor opponents survive 1 headshot past 30m.

MAP POOL CHANGES:
- Haven back in Active Duty with widened Long A Radianite crates.
                    """.trimIndent()
                )
            }

            normalized.contains("league") || normalized.contains("lol") -> {
                val baseline = when {
                    yearStopped <= 2023 -> "Season 13 (Mythic Item Era)"
                    yearStopped == 2024 && (monthStopped == null || monthStopped <= 3) -> "Patch 14.1 (Voidgrubs & Map Symmetrical Rework)"
                    else -> "Patch 14.5 (Smite changes & Lethality buffs)"
                }
                val patchesBehind = if (yearStopped <= 2023) 22 else 8
                PlayerTimelineCatchup(
                    gameTitle = "League of Legends",
                    yearStopped = yearStopped,
                    monthStopped = monthStopped,
                    earliestBaselinePatch = baseline,
                    currentLatestPatch = "Patch 14.10 (Carry Itemization Overhaul)",
                    estimatedPatchesBehind = patchesBehind,
                    majorReworkHighlights = listOf(
                        "Mythic Items Removed: Build diversity restored; ADCs rush 80 AD Infinity Edge.",
                        "Corki Rework: The Package removed entirely; basic attacks converted to 100% physical damage.",
                        "Voidgrubs Added: New neutral objective at 6:00 grants stacking turret pushing true damage.",
                        "Baron Pit Overhaul: Terrain chokes widened with 3 rotating Baron pit terrain shapes.",
                        "Zephyr Returns: Level 15 upgrade for Berserker's Greaves granting 45% AS and flat MS."
                    ),
                    strategicCatchupBrief = "Since leaving in $timeLabel, League deleted the restrictive Mythic system, returned raw AD scaling to crit marksmen, and added Voidgrubs which reward hyper-early top lane priority.",
                    rawComparisonPatchNotes = """
LEAGUE OF LEGENDS RETURNING PLAYER CATCH-UP (Baseline: $baseline -> Patch 14.10)

SYSTEMIC & CHAMPION OVERHAULS:
- Corki: The Package removed. Hextech Munitions converts attacks to physical damage. E shreds flat resistances.
- Infinity Edge: Attack damage increased to 80; Critical strike damage bonus restored to 50%.
- Kraken Slayer: Crit chance removed; provides attack speed and ramping physical execution damage.
- Voidgrubs: Top side neutral spawn at 6:00 shifting jungle pathing away from bot-lane perma-ganks.
                    """.trimIndent()
                )
            }

            normalized.contains("apex") -> {
                val baseline = when {
                    yearStopped <= 2023 -> "Season 18 (Resurrection / Revenant Reborn)"
                    yearStopped == 2024 && (monthStopped == null || monthStopped <= 3) -> "Season 20 (Armor Cores & Legend Upgrades)"
                    else -> "Season 20.1 (Wingman nerfs)"
                }
                val patchesBehind = if (yearStopped <= 2023) 14 else 6
                PlayerTimelineCatchup(
                    gameTitle = "Apex Legends",
                    yearStopped = yearStopped,
                    monthStopped = monthStopped,
                    earliestBaselinePatch = baseline,
                    currentLatestPatch = "Season 21: Upheaval",
                    estimatedPatchesBehind = patchesBehind,
                    majorReworkHighlights = listOf(
                        "Shield Core Evolution: Ground shields removed; players level up shields via EVO harvesters.",
                        "Alter Debut: New Skirmisher/Controller phases through solid walls via Void Passage.",
                        "Havoc TTK Nerf: Hipfire spread widened by 22%, increasing close-quarters TTK by 60ms.",
                        "Broken Moon Overhaul: Promenade POI demolished for Quarantine Zone with dense cover.",
                        "Bloodhound Beast of the Hunt: Threat thermal vision no longer sees through smoke."
                    ),
                    strategicCatchupBrief = "Since you played in $timeLabel, Apex completely reworked armor progression (no more shield swapping from floor loot) and introduced 3D geometric wall phasing with Alter.",
                    rawComparisonPatchNotes = """
APEX LEGENDS CATCH-UP BRIEF (Baseline: $baseline -> Season 21)

LEGEND & WEAPON BALANCE:
- ALTER: Added to roster with Void Passage (phases squad through walls up to 20m) and Void Nexus beacon.
- BLOODHOUND: Beast of the Hunt cannot see through smoke.
- HAVOC: Hipfire spread penalised; bullet damage reduced to 17.
- BROKEN MOON: Promenade demolished; Quarantine Zone added.
                    """.trimIndent()
                )
            }

            normalized.contains("dota") -> {
                val baseline = if (yearStopped <= 2023) "Patch 7.33 (New Frontiers Map Expansion)" else "Patch 7.35 (Item Overhauls)"
                PlayerTimelineCatchup(
                    gameTitle = "Dota 2",
                    yearStopped = yearStopped,
                    monthStopped = monthStopped,
                    earliestBaselinePatch = baseline,
                    currentLatestPatch = "Patch 7.36 (Innate Abilities & Facets)",
                    estimatedPatchesBehind = if (yearStopped <= 2023) 16 else 6,
                    majorReworkHighlights = listOf(
                        "Innate Abilities: All 124 heroes possess an active or passive ability from level 1.",
                        "Hero Facets: 2 to 3 selectable draft customizations altering core abilities per hero.",
                        "Juggernaut Duelist: Innate deals 10% bonus damage when facing enemy targets.",
                        "Tinker Rework: Rearm no longer refreshes items (Blink/BKB); Defense Matrix is now Innate."
                    ),
                    strategicCatchupBrief = "Since leaving in $timeLabel, Dota 2 experienced its largest structural change in years: Innate abilities and draftable Facets transform every hero's identity from minute zero.",
                    rawComparisonPatchNotes = """
DOTA 2 CATCH-UP BRIEF (Baseline: $baseline -> Patch 7.36)

CORE GAMEPLAY MECHANICS:
- INNATE ABILITIES: Every hero now begins with an innate passive or active ability.
- HERO FACETS: Custom playstyle selection during draft.
- TINKER: Rearm only affects abilities.
                    """.trimIndent()
                )
            }

            else -> {
                // Generic catch-up for other titles
                val patchesBehind = (2026 - yearStopped) * 8 + (if (monthStopped != null) (12 - monthStopped) / 2 else 4)
                PlayerTimelineCatchup(
                    gameTitle = title.ifBlank { "Competitive Esports Title" },
                    yearStopped = yearStopped,
                    monthStopped = monthStopped,
                    earliestBaselinePatch = "Baseline Build ($timeLabel)",
                    currentLatestPatch = "Current Competitive Season Update",
                    estimatedPatchesBehind = patchesBehind.coerceAtLeast(3),
                    majorReworkHighlights = listOf(
                        "Core Ability Tuning: Reworked signature character abilities for streamlined counterplay.",
                        "Time-to-Kill (TTK) Balance: Adjusted weapon damage drop-offs and optimal range bands.",
                        "Objective & Economy Restructure: Streamlined resource curve to reward proactive mid-game contests.",
                        "Map Geometry: Updated sightlines and cover density on tournament rotation maps."
                    ),
                    strategicCatchupBrief = "Since you last played in $timeLabel, approximately $patchesBehind balance and content patches were released. Character kits have been modernized with faster cast animations and more distinct counter-matchup viability.",
                    rawComparisonPatchNotes = """
CUMULATIVE COMPETITIVE METASHIFT CATCH-UP: $title
Period: $timeLabel -> Present

PRIMARY ROSTER ADJUSTMENTS:
- Signature ability cast windups and cooldown recovery frames re-timed.
- Secondary draft synergies rebalanced around high-mobility skirmishers.

ARSENAL & MAP DYNAMICS:
- Weapon damage falloff curves adjusted to prevent oppressive long-range dominance.
- Primary objective contest timers compressed to foster decisive engagements.
                    """.trimIndent()
                )
            }
        }
    }
}

