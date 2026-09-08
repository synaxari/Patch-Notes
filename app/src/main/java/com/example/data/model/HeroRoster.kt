package com.example.data.model

data class RosterHero(
    val name: String,
    val gameTitle: String,
    val role: String,
    val signatureAbility: String,
    val defaultTier: String, // "Tier S", "Tier A+", "Tier A", "Tier B+"
    val defaultWinRate: String, // e.g. "53.2%", "51.8%"
    val defaultViabilityNote: String,
    val iconType: String = "SHIELD" // SHIELD, SPEED, PORTAL, SWORD, BULLET, FIRE, MAGIC
)

enum class HeroPatchStatus(val label: String, val badgeColorHex: Long) {
    REWORKED("REWORKED", 0xFFD0BCFF),
    BUFFED("BUFFED", 0xFF85DFBA),
    NERFED("NERFED", 0xFFF2B8B5),
    ADJUSTED("ADJUSTED", 0xFFCCC2DC),
    STABLE("NO DIRECT CHANGES", 0xFF938F99)
}

data class HeroMetaProfile(
    val hero: RosterHero,
    val patchStatus: HeroPatchStatus,
    val statusHeadline: String,
    val currentViabilityTier: String,
    val projectedWinRate: String,
    val winRateDelta: String,
    val tuningItem: HeroTuningItem? = null
)

object HeroRoster {

    val supportedGames = listOf(
        "Valorant",
        "Apex Legends",
        "Overwatch 2",
        "League of Legends",
        "Dota 2"
    )

    val gameRosters: Map<String, List<RosterHero>> = mapOf(
        "Valorant" to listOf(
            RosterHero("Astra", "Valorant", "Controller", "Astral Form & Gravity Well", "Tier A", "50.5%", "Global map presence with cosmic pull and concuss stun.", "MAGIC"),
            RosterHero("Breach", "Valorant", "Initiator", "Fault Line & Rolling Thunder", "Tier A", "50.9%", "Unrivaled site execute concuss and flash through terrain.", "FIRE"),
            RosterHero("Brimstone", "Valorant", "Controller", "Sky Smoke & Orbital Strike", "Tier A", "50.8%", "Instant triple-smoke deploy and post-plant molly lineups.", "FIRE"),
            RosterHero("Chamber", "Valorant", "Sentinel", "Headhunter & Tour De Force", "Tier A", "51.0%", "Eco sniper holding aggressive angles with instant rendezvous.", "BULLET"),
            RosterHero("Clove", "Valorant", "Controller", "Pick-Me-Up & Ruse", "Tier S", "54.2%", "Highest win-rate controller with post-death smoke utility.", "PORTAL"),
            RosterHero("Cypher", "Valorant", "Sentinel", "Trapwire & Spycam", "Tier S", "52.5%", "Stops aggressive site rushes cold with concussive traps.", "SHIELD"),
            RosterHero("Deadlock", "Valorant", "Sentinel", "GravNet & Annihilation", "Tier B+", "49.8%", "Forced crouch sound traps and cocoon capture ultimate.", "SHIELD"),
            RosterHero("Fade", "Valorant", "Initiator", "Haunt & Nightfall", "Tier A+", "51.8%", "Aggressive info gathering and terror trail prowler tracking.", "PORTAL"),
            RosterHero("Gekko", "Valorant", "Initiator", "Wingman & Thrash", "Tier S", "53.2%", "Reusable plant/defuse buddy and repeatable flash globes.", "MAGIC"),
            RosterHero("Harbor", "Valorant", "Controller", "High Tide & Cove", "Tier B+", "49.5%", "Cascading water walls and bullet-blocking shielded bubble.", "SHIELD"),
            RosterHero("Iso", "Valorant", "Duelist", "Double Tap (Shield)", "Tier S", "53.8%", "Instant invulnerable barrier dominates entry duels.", "SHIELD"),
            RosterHero("Jett", "Valorant", "Duelist", "Tailwind & Blade Storm", "Tier S", "52.1%", "Premier vertical and horizontal repositioning initiator.", "SPEED"),
            RosterHero("KAY/O", "Valorant", "Initiator", "ZERO/point & NULL/cmd", "Tier A", "50.3%", "Suppression knife shuts down agent ability kits instantly.", "BULLET"),
            RosterHero("Killjoy", "Valorant", "Sentinel", "Turret & Lockdown", "Tier A", "50.7%", "Automated crossfire control and ultimate site retake.", "SHIELD"),
            RosterHero("Neon", "Valorant", "Duelist", "High Gear (Dual Slides)", "Tier S", "52.9%", "0% mid-slide accuracy penalty enables lethal aggressive pushes.", "SPEED"),
            RosterHero("Omen", "Valorant", "Controller", "Dark Cover & Paranoia", "Tier A+", "51.4%", "Flexible micro-teleports and reliable high-density hollow smokes.", "PORTAL"),
            RosterHero("Phoenix", "Valorant", "Duelist", "Curveball & Run It Back", "Tier A", "51.2%", "Self-sufficient duel entry with blazing wall and second life.", "FIRE"),
            RosterHero("Raze", "Valorant", "Duelist", "Blast Pack & Showstopper", "Tier S", "52.7%", "High explosive mobility and entry rocket launcher burst.", "FIRE"),
            RosterHero("Reyna", "Valorant", "Duelist", "Dismiss & Devour", "Tier A+", "52.1%", "Permanent overheal and unlimited Empress duration.", "PORTAL"),
            RosterHero("Sage", "Valorant", "Sentinel", "Healing Orb & Resurrection", "Tier A", "50.4%", "Stalling slow orbs, barrier wall, and allied revival.", "SHIELD"),
            RosterHero("Skye", "Valorant", "Initiator", "Guiding Light & Seekers", "Tier A", "50.6%", "Controlled hawk flashes and tracking cabbage dog stun.", "MAGIC"),
            RosterHero("Sova", "Valorant", "Initiator", "Recon Bolt & Hunter's Fury", "Tier A", "50.8%", "Essential map-wide recon and post-plant shock lineups.", "BULLET"),
            RosterHero("Viper", "Valorant", "Controller", "Toxic Screen & Viper's Pit", "Tier A+", "51.9%", "High-pressure decaying poison walls on large maps.", "FIRE"),
            RosterHero("Vyse", "Valorant", "Sentinel", "Arc Rose & Steel Garden", "Tier A+", "52.0%", "Primary weapon jamming vine traps and flash roses.", "SHIELD"),
            RosterHero("Yoru", "Valorant", "Duelist", "Gatecrash & Dimensional Drift", "Tier A", "50.9%", "Deceptive sound fakes, flash clones, and safe scouting.", "PORTAL")
        ),
        "Apex Legends" to listOf(
            RosterHero("Alter", "Apex Legends", "Skirmisher", "Void Passage", "Tier S", "53.1%", "Phases entire squads through thick walls to bypass chokepoints.", "PORTAL"),
            RosterHero("Ash", "Apex Legends", "Skirmisher", "Arc Snare & Phase Breach", "Tier A", "50.6%", "Tether snare and instant one-way spatial portal.", "SWORD"),
            RosterHero("Ballistic", "Apex Legends", "Assault", "Whistler & Tempest", "Tier B+", "49.7%", "Overheats enemy barrels and grants squad infinite ammo.", "BULLET"),
            RosterHero("Bangalore", "Apex Legends", "Assault", "Smoke Launcher & Rolling Thunder", "Tier A", "51.0%", "Essential visual denial and area-of-effect defensive bombardment.", "BULLET"),
            RosterHero("Bloodhound", "Apex Legends", "Recon", "Eye of the Allfather & Beast of Hunt", "Tier A", "50.5%", "Thermal tracking and rapid movement speed in smoke encounters.", "BULLET"),
            RosterHero("Catalyst", "Apex Legends", "Controller", "Piercing Spikes & Dark Veil", "Tier A+", "51.8%", "Ferrofluid wall cuts lines of sight and blocks scan abilities.", "SHIELD"),
            RosterHero("Caustic", "Apex Legends", "Controller", "Nox Gas Trap & Gas Grenade", "Tier A", "50.4%", "Area denial gas clouds slow and disorient aggressive pushers.", "FIRE"),
            RosterHero("Conduit", "Apex Legends", "Support", "Radiant Transfer & Jammer", "Tier A+", "52.0%", "Remote temporary shield regeneration keeps aggressors topped up.", "SHIELD"),
            RosterHero("Crypto", "Apex Legends", "Recon", "Surveillance Drone & EMP", "Tier A", "50.8%", "Map-wide banner checks and 50 shield damage EMP blast.", "BULLET"),
            RosterHero("Fuse", "Apex Legends", "Assault", "Knuckle Cluster & The Motherlode", "Tier A", "51.1%", "Constant explosive harassment and ring of fire trap.", "FIRE"),
            RosterHero("Gibraltar", "Apex Legends", "Support", "Dome of Protection & Bombardment", "Tier A", "50.7%", "Invulnerable bubble shield for resets and defensive mortar barrage.", "SHIELD"),
            RosterHero("Horizon", "Apex Legends", "Skirmisher", "Gravity Lift & Black Hole", "Tier S", "52.8%", "Vertical repositioning lift and gravitational vortex pull.", "SPEED"),
            RosterHero("Lifeline", "Apex Legends", "Support", "D.O.C. Heal Drone & Care Package", "Tier S", "53.4%", "Automated hands-free reviving and high-tier armor drops.", "SHIELD"),
            RosterHero("Loba", "Apex Legends", "Support", "Burglar's Best Friend & Black Market", "Tier A", "51.3%", "Instant squad looting and infinite ammo from the boutique.", "PORTAL"),
            RosterHero("Mad Maggie", "Apex Legends", "Assault", "Riot Drill & Wrecking Ball", "Tier A", "51.0%", "Breaches through gibby domes and drops speed boost pads.", "FIRE"),
            RosterHero("Mirage", "Apex Legends", "Skirmisher", "Psyche Out & Life of the Party", "Tier B+", "49.9%", "Holographic confusion and invisible cloaked revives.", "MAGIC"),
            RosterHero("Newcastle", "Apex Legends", "Support", "Mobile Shield & Castle Wall", "Tier A+", "51.7%", "Deployable cover fortress and dragging downed teammates.", "SHIELD"),
            RosterHero("Octane", "Apex Legends", "Skirmisher", "Stim & Launch Pad", "Tier A", "51.2%", "High adrenaline sprint speed and double-jump launch pads.", "SPEED"),
            RosterHero("Pathfinder", "Apex Legends", "Skirmisher", "Grappling Hook & Zipline Gun", "Tier A", "50.8%", "Fastest vertical mobility and instantaneous ring positioning.", "SPEED"),
            RosterHero("Rampart", "Apex Legends", "Controller", "Amped Cover & Mobile Minigun Sheila", "Tier A", "50.9%", "Reinforced bullet-amped barriers and devastating minigun DPS.", "BULLET"),
            RosterHero("Revenant", "Apex Legends", "Skirmisher", "Forged Shadows & Leap", "Tier S", "52.7%", "Dynamic kinetic overshield recharges automatically on knockdowns.", "SHIELD"),
            RosterHero("Seer", "Apex Legends", "Recon", "Heartbeat Sensor & Exhibit", "Tier B+", "49.6%", "Micro-drones reveal footstep vibrations and cancel heals.", "BULLET"),
            RosterHero("Valkyrie", "Apex Legends", "Skirmisher", "VTOL Jets & Skyward Dive", "Tier A", "50.7%", "Jetpack verticality and whole-squad orbital rotation redeploy.", "SPEED"),
            RosterHero("Vantage", "Apex Legends", "Recon", "Echo Relocation & Sniper's Mark", "Tier A", "50.6%", "High ground bat dash and custom sniper rifle damage boost.", "BULLET"),
            RosterHero("Wattson", "Apex Legends", "Controller", "Perimeter Security & Pylon", "Tier A", "50.8%", "Electric node fences and interception pylon destroys ordnance.", "SHIELD"),
            RosterHero("Wraith", "Apex Legends", "Skirmisher", "Into the Void & Dimensional Rift", "Tier A+", "51.4%", "Zero-damage invulnerability phase and safe team rotations.", "PORTAL")
        ),
        "Overwatch 2" to listOf(
            // Tanks
            RosterHero("D.Va", "Overwatch 2", "Tank", "Defense Matrix & Boosters", "Tier S", "52.4%", "Swallows projectile bursts and flies directly onto vulnerable snipers.", "SHIELD"),
            RosterHero("Doomfist", "Overwatch 2", "Tank", "Power Block & Meteor Strike", "Tier A", "51.1%", "Aggressive brawler disruption and empowered rocket punches.", "SWORD"),
            RosterHero("Junker Queen", "Overwatch 2", "Tank", "Jagged Blade & Carnage", "Tier A", "51.3%", "High bleed sustain and wound healing in frontline brawls.", "SWORD"),
            RosterHero("Mauga", "Overwatch 2", "Tank", "Chainguns & Cage Fight", "Tier A", "50.9%", "Ignition and critical bullets with inescapable cage barrier.", "FIRE"),
            RosterHero("Orisa", "Overwatch 2", "Tank", "Fortify & Javelin Spin", "Tier A", "50.7%", "Crowd control immunity and aggressive knockback javelins.", "SHIELD"),
            RosterHero("Ramattra", "Overwatch 2", "Tank", "Nemesis Form & Annihilation", "Tier A", "51.2%", "Pummeling punches pierce barriers with infinite aura ultimate.", "SWORD"),
            RosterHero("Reinhardt", "Overwatch 2", "Tank", "Barrier Shield & Earthshatter", "Tier A", "50.6%", "Classic frontline anchor and heavy hammer crowd control.", "SHIELD"),
            RosterHero("Roadhog", "Overwatch 2", "Tank", "Chain Hook & Take a Breather", "Tier A", "50.8%", "Instant pick displacement hooks and pig pen trap combos.", "SWORD"),
            RosterHero("Sigma", "Overwatch 2", "Tank", "Experimental Barrier & Gravitic Flux", "Tier S", "52.2%", "Flexible repositionable shield and kinetic grasp absorption.", "SHIELD"),
            RosterHero("Winston", "Overwatch 2", "Tank", "Jump Pack & Barrier Projector", "Tier S", "52.0%", "Primary dive tank with bubble dome isolation and primal rage.", "SHIELD"),
            RosterHero("Wrecking Ball", "Overwatch 2", "Tank", "Grappling Claw & Minefield", "Tier A", "50.5%", "High speed bowling disruption and adaptive overshields.", "SPEED"),
            RosterHero("Zarya", "Overwatch 2", "Tank", "Particle Barrier & Graviton Surge", "Tier A+", "51.8%", "Dual projected bubble shields convert damage into 100 energy beam.", "SHIELD"),
            // Damage
            RosterHero("Ashe", "Overwatch 2", "Damage", "Viper Rifle & B.O.B.", "Tier S", "52.5%", "Deadly dynamite burns and high-accuracy lever-action headshots.", "FIRE"),
            RosterHero("Bastion", "Overwatch 2", "Damage", "Assault Configuration & Artillery", "Tier A", "50.7%", "Unrivaled tank-busting minigun shred and tactical grenade.", "BULLET"),
            RosterHero("Cassidy", "Overwatch 2", "Damage", "Magnetic Grenade & Peacekeeper", "Tier A", "51.3%", "High single-shot hitscan accuracy and mobility hindering grenade.", "BULLET"),
            RosterHero("Echo", "Overwatch 2", "Damage", "Focusing Beam & Duplicate", "Tier A+", "51.9%", "Melts targets under 50% health and copies enemy tank ultimates.", "SPEED"),
            RosterHero("Genji", "Overwatch 2", "Damage", "Deflect & Swift Strike", "Tier A+", "51.1%", "Dynamic wall climbing, shuriken bursts, and dash resets.", "SWORD"),
            RosterHero("Hanzo", "Overwatch 2", "Damage", "Storm Bow & Sonic Arrow", "Tier A", "50.3%", "Vision arrow info and lethal projectile storm headshots.", "BULLET"),
            RosterHero("Junkrat", "Overwatch 2", "Damage", "Frag Launcher & RIP-Tire", "Tier A", "50.8%", "Chokepoint bouncing grenade denial and steel trap immobilize.", "FIRE"),
            RosterHero("Mei", "Overwatch 2", "Damage", "Endothermic Blaster & Ice Wall", "Tier A", "51.0%", "Isolates overextending tanks and freezes point in Blizzard.", "SHIELD"),
            RosterHero("Pharah", "Overwatch 2", "Damage", "Rocket Launcher & Jet Dash", "Tier A", "51.2%", "Aerial rocket bombardment with horizontal jet evasions.", "FIRE"),
            RosterHero("Reaper", "Overwatch 2", "Damage", "Hellfire Shotguns & Death Blossom", "Tier A", "50.9%", "Point-blank tank shredding with wraith form invulnerability.", "FIRE"),
            RosterHero("Sojourn", "Overwatch 2", "Damage", "Railgun & Power Slide", "Tier S", "52.8%", "Piercing 130-damage railgun headshots and disruptive energy shot.", "BULLET"),
            RosterHero("Soldier: 76", "Overwatch 2", "Damage", "Heavy Pulse Rifle & Tactical Visor", "Tier A", "51.0%", "Consistent mid-range tracking, helix rockets, and biotic heal.", "BULLET"),
            RosterHero("Sombra", "Overwatch 2", "Damage", "Virus & Stealth Hack", "Tier A", "50.6%", "Permanent invisibility, ability lockout, and EMP shield wipe.", "MAGIC"),
            RosterHero("Symmetra", "Overwatch 2", "Damage", "Sentry Turrets & Teleporter", "Tier A", "51.1%", "High ramp-up laser beam and map-wide photon barrier wall.", "PORTAL"),
            RosterHero("Torbjörn", "Overwatch 2", "Damage", "Rivet Gun & Deploy Turret", "Tier A", "51.4%", "Automated crossfire turret and molten core area denial.", "FIRE"),
            RosterHero("Tracer", "Overwatch 2", "Damage", "Blink & Recall", "Tier S", "52.8%", "High APM backline flanker with full health rewinds.", "SPEED"),
            RosterHero("Venture", "Overwatch 2", "Damage", "Burrow & Tectonic Shock", "Tier A+", "52.1%", "Invulnerable underground burrowing and seismic dash combos.", "SWORD"),
            RosterHero("Widowmaker", "Overwatch 2", "Damage", "Sniper Rifle & Infra-Sight", "Tier A+", "51.6%", "Instant 300-damage headshot kills across long sightlines.", "BULLET"),
            // Supports
            RosterHero("Ana", "Overwatch 2", "Support", "Biotic Grenade & Sleep Dart", "Tier S", "52.9%", "Anti-heal stops all enemy recovery and cancels enemy ultimates.", "MAGIC"),
            RosterHero("Baptiste", "Overwatch 2", "Support", "Immortality Field & Amplification", "Tier S", "52.6%", "Prevents team death with lamp and doubles allied projectile DPS.", "SHIELD"),
            RosterHero("Brigitte", "Overwatch 2", "Support", "Shield Bash & Rally", "Tier A+", "51.8%", "Peels dive flankers away from co-support with inspire aura.", "SHIELD"),
            RosterHero("Illari", "Overwatch 2", "Support", "Healing Pylon & Captive Sun", "Tier A", "51.4%", "Automated healing turret and explosive solar chain explosions.", "FIRE"),
            RosterHero("Juno", "Overwatch 2", "Support", "Mediblaster & Hyper Ring", "Tier S", "53.1%", "Squad speed boost rings and orbital ray beam amplification.", "SPEED"),
            RosterHero("Kiriko", "Overwatch 2", "Support", "Protection Suzu & Kitsune Rush", "Tier S", "53.5%", "Cleanse invulnerability cancels negative debuffs and enemy ultimates.", "MAGIC"),
            RosterHero("Lifeweaver", "Overwatch 2", "Support", "Life Grip & Petal Platform", "Tier B+", "49.7%", "Pulls teammates out of fatal situations onto elevated platforms.", "MAGIC"),
            RosterHero("Lúcio", "Overwatch 2", "Support", "Speed Boost & Sound Barrier", "Tier S", "52.7%", "Wall-riding team speed transitions and massive overhealth drop.", "SPEED"),
            RosterHero("Mercy", "Overwatch 2", "Support", "Caduceus Staff & Resurrect", "Tier A", "50.8%", "30% damage boost pocketing and brings fallen tank back to life.", "MAGIC"),
            RosterHero("Moira", "Overwatch 2", "Support", "Biotic Grasp & Coalescence", "Tier A", "51.2%", "Massive burst AoE healing and fading through enemy focus.", "MAGIC"),
            RosterHero("Zenyatta", "Overwatch 2", "Support", "Discord Orb & Transcendence", "Tier A+", "52.1%", "Amplifies damage taken by 25% and heals 300 HP/s in ult.", "MAGIC")
        ),
        "League of Legends" to listOf(
            RosterHero("Aatrox", "League of Legends", "Top / Fighter", "The Darkin Blade & World Ender", "Tier A", "50.9%", "High drain-tank sustain in prolonged Baron and dragon brawls.", "SWORD"),
            RosterHero("Ahri", "League of Legends", "Mid / Mage", "Spirit Rush & Charm", "Tier A+", "51.7%", "Exceptional pick potential and 3-dash skirmish survivability.", "MAGIC"),
            RosterHero("Akali", "League of Legends", "Mid / Assassin", "Twilight Shroud & Perfect Execution", "Tier A", "50.6%", "Stealth shroud energy control and executing mobility.", "SWORD"),
            RosterHero("Caitlyn", "League of Legends", "Bot / Marksman", "Headshot & 90 Caliber Net", "Tier S", "52.3%", "Longest base attack range with deadly critical strike scalings.", "BULLET"),
            RosterHero("Corki", "League of Legends", "Mid / Marksman", "Hextech Munitions", "Tier S", "52.8%", "Rebuilt into pure AD powerhouse with 100% physical conversion.", "BULLET"),
            RosterHero("Darius", "League of Legends", "Top / Juggernaut", "Hemorrhage & Noxian Guillotine", "Tier A", "51.2%", "Noxian Might 5-bleed stack true damage dunk resets.", "SWORD"),
            RosterHero("Ezreal", "League of Legends", "Bot / Marksman", "Mystic Shot & Arcane Shift", "Tier A+", "51.9%", "Safest ADC with blink mobility and high poke range.", "MAGIC"),
            RosterHero("Garen", "League of Legends", "Top / Juggernaut", "Decisive Strike & Demacian Justice", "Tier A", "51.4%", "Silence, spin armor shred, and execute true damage sword.", "SWORD"),
            RosterHero("Jinx", "League of Legends", "Bot / Marksman", "Get Excited & Fishbones", "Tier S", "53.4%", "Synergizes with 80 AD Infinity Edge for explosive teamfight resets.", "BULLET"),
            RosterHero("Kai'Sa", "League of Legends", "Bot / Marksman", "Supercharge & Killer Instinct", "Tier S", "52.6%", "Plasma passive burst, stealth reposition, and backline shield dash.", "BULLET"),
            RosterHero("K'Sante", "League of Legends", "Top / Tank", "Ntofo Strikes & All Out", "Tier A+", "51.5%", "High frontline tankiness converts into explosive fighter duel.", "SHIELD"),
            RosterHero("Lee Sin", "League of Legends", "Jungle / Fighter", "Sonic Wave & Dragon's Rage", "Tier A+", "51.1%", "Dominant early map tempo and objective control around Voidgrubs.", "SWORD"),
            RosterHero("Lux", "League of Legends", "Support / Mage", "Light Binding & Final Spark", "Tier A", "51.0%", "Long-range snare burst and laser waveclear.", "MAGIC"),
            RosterHero("Malphite", "League of Legends", "Top / Tank", "Ground Slam & Unstoppable Force", "Tier A", "51.3%", "Instant multi-target knockup initiates teamfights across screens.", "SHIELD"),
            RosterHero("Nautilus", "League of Legends", "Support / Tank", "Dredge Line & Depth Charge", "Tier S", "52.5%", "Anchor displacement hook and point-and-click knockup ult.", "SHIELD"),
            RosterHero("Riven", "League of Legends", "Top / Fighter", "Broken Wings & Blade of the Exile", "Tier A", "50.8%", "High APM animation canceling, shields, and wind slash execute.", "SWORD"),
            RosterHero("Sett", "League of Legends", "Top / Juggernaut", "Haymaker & The Show Stopper", "Tier A", "51.2%", "Converts damage taken into massive true-damage haymaker shield.", "SWORD"),
            RosterHero("Sylas", "League of Legends", "Mid / Mage", "Kingslayer & Hijack", "Tier A+", "51.8%", "Steals game-changing enemy ultimates with heal bursts.", "MAGIC"),
            RosterHero("Thresh", "League of Legends", "Support / Tank", "Death Sentence & Dark Passage", "Tier A", "51.0%", "Unrivaled playmaking hooks and high-ground lantern saves.", "SHIELD"),
            RosterHero("Vayne", "League of Legends", "Bot / Marksman", "Silver Bolts & Final Hour", "Tier A", "50.9%", "Percent maximum health true damage and tumble stealth.", "BULLET"),
            RosterHero("Yasuo", "League of Legends", "Mid / Fighter", "Wind Wall & Last Breath", "Tier B+", "49.6%", "High skill-ceiling projectile denial with 100% crit chance doubling.", "SWORD"),
            RosterHero("Yone", "League of Legends", "Mid / Fighter", "Soul Unbound & Fate Sealed", "Tier A+", "51.4%", "Untargetable spirit ramp, knockup dash, and hybrid crit damage.", "SWORD"),
            RosterHero("Zed", "League of Legends", "Mid / Assassin", "Living Shadow & Death Mark", "Tier A", "50.7%", "Shadow swapping combos and delayed pop death mark executions.", "SWORD")
        ),
        "Dota 2" to Dota2Roster.heroes
    )

    fun getHeroesForGame(game: String): List<RosterHero> {
        val key = gameRosters.keys.find { it.equals(game, ignoreCase = true) }
        return if (key != null) gameRosters[key] ?: emptyList() else gameRosters["Valorant"]!!
    }

    fun getAllHeroRolesForGame(game: String): List<String> {
        val heroes = getHeroesForGame(game)
        val roles = heroes.map { it.role.substringBefore("/").trim() }.distinct().sorted()
        return listOf("All") + roles
    }

    fun evaluateHeroMeta(
        hero: RosterHero,
        report: PatchAnalysisReport?
    ): HeroMetaProfile {
        if (report == null) {
            return HeroMetaProfile(
                hero = hero,
                patchStatus = HeroPatchStatus.STABLE,
                statusHeadline = "Meta Viable: ${hero.defaultViabilityNote}",
                currentViabilityTier = hero.defaultTier,
                projectedWinRate = hero.defaultWinRate,
                winRateDelta = "0.0% (Current Baseline)"
            )
        }

        // Check if hero was tuned in the active report
        val tuned = report.characterTuning.find { it.name.contains(hero.name, ignoreCase = true) }
        return if (tuned != null) {
            val status = when (tuned.changeType.uppercase()) {
                "REWORK" -> HeroPatchStatus.REWORKED
                "BUFF" -> HeroPatchStatus.BUFFED
                "NERF" -> HeroPatchStatus.NERFED
                else -> HeroPatchStatus.ADJUSTED
            }

            val headline = when (status) {
                HeroPatchStatus.REWORKED -> "Reworked: ${tuned.abilityName.ifBlank { tuned.abilityImpact.take(45) }}"
                HeroPatchStatus.BUFFED -> "Buffed: ${tuned.abilityImpact.take(55)}"
                HeroPatchStatus.NERFED -> "Nerfed: ${tuned.abilityImpact.take(55)}"
                HeroPatchStatus.ADJUSTED -> "Adjusted: ${tuned.abilityImpact.take(55)}"
                HeroPatchStatus.STABLE -> "Stable: ${hero.defaultViabilityNote}"
            }

            HeroMetaProfile(
                hero = hero,
                patchStatus = status,
                statusHeadline = headline,
                currentViabilityTier = tuned.competitiveViability.ifBlank { hero.defaultTier },
                projectedWinRate = hero.defaultWinRate,
                winRateDelta = tuned.winRateForecastDelta,
                tuningItem = tuned
            )
        } else {
            HeroMetaProfile(
                hero = hero,
                patchStatus = HeroPatchStatus.STABLE,
                statusHeadline = "Untouched in this patch; maintains ${hero.defaultTier} standing (${hero.defaultViabilityNote})",
                currentViabilityTier = hero.defaultTier,
                projectedWinRate = hero.defaultWinRate,
                winRateDelta = "0.0% (Stable)"
            )
        }
    }
}
