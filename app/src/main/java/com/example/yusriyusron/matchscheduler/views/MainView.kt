package com.example.yusriyusron.matchscheduler.views

import com.example.yusriyusron.matchscheduler.models.Match

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
}