package personal.rowan.showdownmetrics

/**
 * Created by Rowan Hall
 */

const val SAMPLE_REPLAY = "|j|☆DoerreKong\n" +
        "|j|☆froonk53\n" +
        "|player|p1|DoerreKong|156|1510\n" +
        "|player|p2|froonk53|101|1189\n" +
        "|teamsize|p1|6\n" +
        "|teamsize|p2|6\n" +
        "|gametype|doubles\n" +
        "|gen|8\n" +
        "|tier|[Gen 8] Doubles Ubers\n" +
        "|rated|\n" +
        "|clearpoke\n" +
        "|poke|p1|Necrozma-Dusk-Mane|\n" +
        "|poke|p1|Torkoal, F|\n" +
        "|poke|p1|Lunala|\n" +
        "|poke|p1|Marshadow|\n" +
        "|poke|p1|Solgaleo|\n" +
        "|poke|p1|Dragapult, F|\n" +
        "|poke|p2|Dracovish|\n" +
        "|poke|p2|Whimsicott, F|\n" +
        "|poke|p2|Lucario, F|\n" +
        "|poke|p2|Grimmsnarl, M|\n" +
        "|poke|p2|Snorlax, M|\n" +
        "|poke|p2|Rotom-Wash|\n" +
        "|rule|Species Clause: Limit one of each Pokémon\n" +
        "|rule|OHKO Clause: OHKO moves are banned\n" +
        "|rule|Evasion Moves Clause: Evasion moves are banned\n" +
        "|rule|Endless Battle Clause: Forcing endless battles is banned\n" +
        "|rule|HP Percentage Mod: HP is shown in percentages\n" +
        "|teampreview\n" +
        "|\n" +
        "|start\n" +
        "|switch|p1a: Dragapult|Dragapult, F|100/100\n" +
        "|switch|p1b: Solgaleo|Solgaleo|100/100\n" +
        "|switch|p2a: Grimmsnarl|Grimmsnarl, M, shiny|100/100\n" +
        "|switch|p2b: Rotom|Rotom-Wash, shiny|100/100\n" +
        "|turn|1\n" +
        "|inactive|Battle timer is ON: inactive players will automatically lose when time's up. (requested by DoerreKong)\n" +
        "|\n" +
        "|-start|p1b: Solgaleo|Dynamax\n" +
        "|-heal|p1b: Solgaleo|100/100|[silent]\n" +
        "|move|p2a: Grimmsnarl|Thunder Wave|p1a: Dragapult\n" +
        "|-status|p1a: Dragapult|par\n" +
        "|move|p1b: Solgaleo|Max Steelspike|p2a: Grimmsnarl\n" +
        "|-supereffective|p2a: Grimmsnarl\n" +
        "|-damage|p2a: Grimmsnarl|3/100\n" +
        "|-boost|p1a: Dragapult|def|1\n" +
        "|-boost|p1b: Solgaleo|def|1\n" +
        "|move|p2b: Rotom|Hex|p1a: Dragapult\n" +
        "|-supereffective|p1a: Dragapult\n" +
        "|-enditem|p1a: Dragapult|Focus Sash\n" +
        "|-damage|p1a: Dragapult|1/100 par\n" +
        "|move|p1a: Dragapult|Dragon Darts|p2b: Rotom\n" +
        "|-damage|p2b: Rotom|72/100\n" +
        "|-anim|p1a: Dragapult|Dragon Darts|p2b: Rotom\n" +
        "|-damage|p2b: Rotom|45/100\n" +
        "|\n" +
        "|-heal|p2b: Rotom|51/100|[from] item: Leftovers\n" +
        "|upkeep\n" +
        "|turn|2\n" +
        "|j| YTSypheX\n" +
        "|inactive|DoerreKong has 120 seconds left.\n" +
        "|\n" +
        "|move|p1a: Dragapult|Sucker Punch||[still]\n" +
        "|-fail|p1a: Dragapult\n" +
        "|move|p2a: Grimmsnarl|Thunder Wave|p1b: Solgaleo|[miss]\n" +
        "|-miss|p2a: Grimmsnarl|p1b: Solgaleo\n" +
        "|move|p1b: Solgaleo|Max Darkness|p2b: Rotom\n" +
        "|-damage|p2b: Rotom|11/100\n" +
        "|-unboost|p2a: Grimmsnarl|spd|1\n" +
        "|-unboost|p2b: Rotom|spd|1\n" +
        "|move|p2b: Rotom|Hex|p1a: Dragapult\n" +
        "|-supereffective|p1a: Dragapult\n" +
        "|-damage|p1a: Dragapult|0 fnt\n" +
        "|faint|p1a: Dragapult\n" +
        "|\n" +
        "|-heal|p2b: Rotom|17/100|[from] item: Leftovers\n" +
        "|upkeep\n" +
        "|\n" +
        "|switch|p1a: Lunala|Lunala|100/100\n" +
        "|turn|3\n" +
        "|\n" +
        "|move|p2a: Grimmsnarl|Thunder Wave|p1a: Lunala\n" +
        "|-status|p1a: Lunala|par\n" +
        "|move|p1b: Solgaleo|Max Steelspike|p2a: Grimmsnarl\n" +
        "|-supereffective|p2a: Grimmsnarl\n" +
        "|-damage|p2a: Grimmsnarl|0 fnt\n" +
        "|-boost|p1a: Lunala|def|1\n" +
        "|-boost|p1b: Solgaleo|def|1\n" +
        "|faint|p2a: Grimmsnarl\n" +
        "|move|p2b: Rotom|Hex|p1a: Lunala\n" +
        "|-supereffective|p1a: Lunala\n" +
        "|-damage|p1a: Lunala|29/100 par\n" +
        "|-enditem|p1a: Lunala|Weakness Policy\n" +
        "|-boost|p1a: Lunala|atk|2|[from] item: Weakness Policy\n" +
        "|-boost|p1a: Lunala|spa|2|[from] item: Weakness Policy\n" +
        "|move|p1a: Lunala|Trick Room|p1a: Lunala\n" +
        "|-fieldstart|move: Trick Room|[of] p1a: Lunala\n" +
        "|\n" +
        "|-heal|p2b: Rotom|23/100|[from] item: Leftovers\n" +
        "|-end|p1b: Solgaleo|Dynamax\n" +
        "|-heal|p1b: Solgaleo|100/100|[silent]\n" +
        "|upkeep\n" +
        "|l| YTSypheX\n" +
        "|inactive|froonk53 has 120 seconds left.\n" +
        "|\n" +
        "|switch|p2a: Snorlax|Snorlax, M|100/100\n" +
        "|turn|4\n" +
        "|\n" +
        "|move|p2a: Snorlax|Earthquake|p1b: Solgaleo|[spread] p1a,p1b\n" +
        "|-immune|p2b: Rotom|[from] ability: Levitate\n" +
        "|-supereffective|p1b: Solgaleo\n" +
        "|-damage|p1a: Lunala|17/100 par\n" +
        "|-damage|p1b: Solgaleo|88/100\n" +
        "|-enditem|p1b: Solgaleo|Weakness Policy\n" +
        "|-boost|p1b: Solgaleo|atk|2|[from] item: Weakness Policy\n" +
        "|-boost|p1b: Solgaleo|spa|2|[from] item: Weakness Policy\n" +
        "|move|p1a: Lunala|Will-O-Wisp|p2a: Snorlax\n" +
        "|-status|p2a: Snorlax|brn\n" +
        "|move|p2b: Rotom|Hex|p1a: Lunala\n" +
        "|-supereffective|p1a: Lunala\n" +
        "|-damage|p1a: Lunala|0 fnt\n" +
        "|faint|p1a: Lunala\n" +
        "|move|p1b: Solgaleo|Knock Off|p2b: Rotom\n" +
        "|-damage|p2b: Rotom|0 fnt\n" +
        "|-enditem|p2b: Rotom|Leftovers|[from] move: Knock Off|[of] p1b: Solgaleo\n" +
        "|faint|p2b: Rotom\n" +
        "|\n" +
        "|-damage|p2a: Snorlax|94/100 brn|[from] brn\n" +
        "|upkeep\n" +
        "|inactive|froonk53 has 120 seconds left.\n" +
        "|\n" +
        "|switch|p2b: Lucario|Lucario, F, shiny|100/100\n" +
        "|switch|p1a: Torkoal|Torkoal, F|100/100\n" +
        "|-weather|SunnyDay|[from] ability: Drought|[of] p1a: Torkoal\n" +
        "|turn|5\n" +
        "|\n" +
        "|switch|p2b: Dracovish|Dracovish, shiny|100/100\n" +
        "|move|p1a: Torkoal|Eruption|p2b: Dracovish|[spread] p2a,p2b\n" +
        "|-resisted|p2b: Dracovish\n" +
        "|-damage|p2a: Snorlax|52/100 brn\n" +
        "|-damage|p2b: Dracovish|77/100\n" +
        "|move|p2a: Snorlax|Belly Drum|p2a: Snorlax\n" +
        "|-damage|p2a: Snorlax|2/100 brn\n" +
        "|-setboost|p2a: Snorlax|atk|6|[from] move: Belly Drum\n" +
        "|-enditem|p2a: Snorlax|Figy Berry|[eat]\n" +
        "|-heal|p2a: Snorlax|35/100 brn|[from] item: Figy Berry\n" +
        "|move|p1b: Solgaleo|Knock Off|p2a: Snorlax\n" +
        "|-damage|p2a: Snorlax|8/100 brn\n" +
        "|\n" +
        "|-weather|SunnyDay|[upkeep]\n" +
        "|-damage|p2a: Snorlax|1/100 brn|[from] brn\n" +
        "|upkeep\n" +
        "|turn|6\n" +
        "|\n" +
        "|move|p1a: Torkoal|Eruption|p2b: Dracovish|[spread] p2a,p2b\n" +
        "|-resisted|p2b: Dracovish\n" +
        "|-damage|p2a: Snorlax|0 fnt\n" +
        "|-damage|p2b: Dracovish|54/100\n" +
        "|faint|p2a: Snorlax\n" +
        "|move|p1b: Solgaleo|Knock Off|p2b: Dracovish\n" +
        "|-damage|p2b: Dracovish|0 fnt\n" +
        "|-enditem|p2b: Dracovish|Choice Scarf|[from] move: Knock Off|[of] p1b: Solgaleo\n" +
        "|faint|p2b: Dracovish\n" +
        "|\n" +
        "|-weather|SunnyDay|[upkeep]\n" +
        "|upkeep\n" +
        "|\n" +
        "|switch|p2a: Whimsicott|Whimsicott, F, shiny|100/100\n" +
        "|switch|p2b: Lucario|Lucario, F, shiny|100/100\n" +
        "|turn|7\n" +
        "|c|☆froonk53|gg\n" +
        "|c|☆DoerreKong|gg\n" +
        "|\n" +
        "|-start|p2b: Lucario|Dynamax\n" +
        "|-heal|p2b: Lucario|100/100|[silent]\n" +
        "|move|p1a: Torkoal|Eruption|p2b: Lucario|[spread] p2a,p2b\n" +
        "|-supereffective|p2a: Whimsicott\n" +
        "|-supereffective|p2b: Lucario\n" +
        "|-enditem|p2a: Whimsicott|Focus Sash\n" +
        "|-damage|p2a: Whimsicott|1/100\n" +
        "|-damage|p2b: Lucario|0 fnt\n" +
        "|faint|p2b: Lucario\n" +
        "|move|p1b: Solgaleo|Sunsteel Strike|p2a: Whimsicott\n" +
        "|-supereffective|p2a: Whimsicott\n" +
        "|-damage|p2a: Whimsicott|0 fnt\n" +
        "|faint|p2a: Whimsicott\n" +
        "|\n" +
        "|win|DoerreKong\n" +
        "|raw|DoerreKong's rating: 1511 &rarr; <strong>1516</strong><br />(+5 for winning)\n" +
        "|raw|froonk53's rating: 1189 &rarr; <strong>1182</strong><br />(-7 for losing)\n" +
        "|l|☆froonk53"