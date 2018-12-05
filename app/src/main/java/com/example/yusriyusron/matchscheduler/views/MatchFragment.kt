package com.example.yusriyusron.matchscheduler.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoContext

class MatchFragment : Fragment() {
    private lateinit var type : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments!!.getString("TYPE", "last")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return MainFragment(type).createView(AnkoContext.create(container!!.context,container,false))
    }
}