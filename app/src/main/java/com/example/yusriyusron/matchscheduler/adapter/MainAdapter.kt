package com.example.yusriyusron.matchscheduler.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.yusriyusron.matchscheduler.R
import com.example.yusriyusron.matchscheduler.models.Match
import org.jetbrains.anko.*
import java.text.SimpleDateFormat

class MainAdapter(
    private val events: List<Match>,
    private val handler: com.example.yusriyusron.matchscheduler.adapter.Handler
) : RecyclerView.Adapter<MainAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MatchViewHolder {
        return MatchViewHolder(MatchInterface().createView(AnkoContext.create(p0.context, p0)))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(p0: MatchViewHolder, p1: Int) {
        val match = events[p1]
        p0.bindItem(match)
        p0.itemView.setOnClickListener {
            handler.click(match)
        }
    }

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val homeTeam: TextView = view.find(R.id.homeTeam)
        private val awayTeam: TextView = view.find(R.id.awayTeam)
        private val scoreTeam: TextView = view.find(R.id.scoreTeam)
        private val times: TextView = view.find(R.id.times)

        fun bindItem(match: Match) {
            val date = SimpleDateFormat("yyyy-MM-dd").parse(match.dateEvent)
            times.text = String.format("%ta, %te %tb %tY", date, date, date, date)
            val teamName = match.eventName?.split(" vs ")
            teamName?.let {
                homeTeam.text = it[0]
                awayTeam.text = it[1]
            }
            match.homeScore?.let {
                scoreTeam.text = match.homeScore + " vs " + match.awayScore
            }
        }
    }

    class MatchInterface : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER_HORIZONTAL
                    textView {
                        id = R.id.times
                        textColor = Color.BLACK
                        textSize = 16f
                    }.lparams {
                        topPadding = dip(16)
                    }

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        bottomPadding = dip(16)
                        textView {
                            id = R.id.homeTeam
                            textSize = 16f
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams(width = dip(0),height = wrapContent,weight = 4f)
                        textView {
                            id = R.id.scoreTeam
                            textSize = 16f
                            text = "VS"
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams(width = dip(0),height = wrapContent, weight = 2f) {
                            leftMargin = dip(10)
                            rightMargin = dip(10)
                        }
                        textView {
                            id = R.id.awayTeam
                            textSize = 16f
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams(width = dip(0),height = wrapContent,weight = 4f)
                    }
                }
            }
        }
    }
}

