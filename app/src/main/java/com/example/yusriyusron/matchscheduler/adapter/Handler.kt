package com.example.yusriyusron.matchscheduler.adapter

import com.example.yusriyusron.matchscheduler.models.Match

interface Handler {
    fun click(match: Match)
}