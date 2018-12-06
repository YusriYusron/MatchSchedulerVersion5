package com.example.yusriyusron.matchscheduler.views

import com.example.yusriyusron.matchscheduler.models.Match
import com.example.yusriyusron.matchscheduler.models.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
    fun showTeamList(data: List<Team>)
}