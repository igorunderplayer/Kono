package me.igorunderplayer.kono.utils

import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard

fun regionFromLeagueShard (leagueShard: LeagueShard): RegionShard {
    return if (leagueShard == LeagueShard.BR1 || leagueShard == LeagueShard.NA1 || leagueShard == LeagueShard.LA1 || leagueShard == LeagueShard.LA2) {
        RegionShard.AMERICAS
    }  else if (leagueShard == LeagueShard.EUN1 || leagueShard == LeagueShard.EUW1  || leagueShard == LeagueShard.TR1  || leagueShard == LeagueShard.RU) {
        RegionShard.EUROPE
    } else if (leagueShard == LeagueShard.JP1 || leagueShard == LeagueShard.KR || leagueShard == LeagueShard.SG2 || leagueShard == LeagueShard.PH2 || leagueShard == LeagueShard.TW2 || leagueShard == LeagueShard.TH2 || leagueShard == LeagueShard.VN2 || leagueShard == LeagueShard.ID1 ) {
        RegionShard.ASIA
    } else if (leagueShard == LeagueShard.PBE1) {
        RegionShard.PBE
    } else {
        RegionShard.UNKNOWN
    }
}