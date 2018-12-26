package com.example.yusriyusron.matchscheduler.database

data class Favorite(val id: Long?, val matchId: String?, val eventName: String?,
                    val dateEvent: String?, val homeTeam: String?, val homeScore: String?,
                    val awayTeam: String?, val awayScore: String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val EVENT_NAME: String = "EVENT_NAME"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
    }
}