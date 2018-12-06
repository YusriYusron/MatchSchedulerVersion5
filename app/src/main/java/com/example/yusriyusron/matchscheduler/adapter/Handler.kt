package com.example.yusriyusron.matchscheduler.adapter

import com.example.yusriyusron.matchscheduler.database.Favorite
import com.example.yusriyusron.matchscheduler.models.Match
import com.example.yusriyusron.matchscheduler.models.Team

interface Handler {
    fun click(match: Match)
    fun getTeam(idTeam: String?): Team
    fun click(match: Favorite)
}