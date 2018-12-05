package com.example.yusriyusron.matchscheduler.api

import android.net.Uri
import com.example.yusriyusron.matchscheduler.BuildConfig

object TheSportDBApi {
    fun getNextMatch(): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("eventsnextleague.php")
            .appendQueryParameter("id", "4328")
            .build()
            .toString()
    }

    fun getLastMatch():String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("eventspastleague.php")
            .appendQueryParameter("id", "4328")
            .build()
            .toString()
    }

    fun getTeams():String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookup_all_teams.php")
            .appendQueryParameter("id", "4328")
            .build()
            .toString()
    }

    fun getATeam(idTeam: String?):String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookupteam.php")
            .appendQueryParameter("id", idTeam)
            .build()
            .toString()
    }
}