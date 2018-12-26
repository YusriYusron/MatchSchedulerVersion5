package com.example.yusriyusron.matchscheduler.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.yusriyusron.matchscheduler.R
import com.example.yusriyusron.matchscheduler.database.Favorite
import com.example.yusriyusron.matchscheduler.views.MainFragment
import org.jetbrains.anko.*
import java.text.SimpleDateFormat

class FavoriteAdapter (private val matches: List<Favorite>, private val handler: MainFragment)
    : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(MatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val match = matches[position]
        holder.bindItem(match)
        holder.itemView.setOnClickListener {
            handler.click(match)
        }
    }

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val time: TextView = view.find(R.id.time)
        private val homeTeam: TextView = view.find(R.id.homeTeam)
        private val awayTeam: TextView = view.find(R.id.awayTeam)
        private val scoreTeam: TextView = view.find(R.id.scoreTeam)

        fun bindItem(match: Favorite) {
            val date = SimpleDateFormat("yyyy-MM-dd").parse(match.dateEvent)
            time.text = String.format("%ta, %te %tb %tY", date, date, date, date)
            val teamName = match.eventName?.split(" vs ")
            teamName?.let {
                homeTeam.text = it[0]
                awayTeam.text = it[1]
            }
            match.homeScore?.let {
                scoreTeam.text = match.homeScore + " VS " + match.awayScore
            }
        }
    }

    class MatchUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER_HORIZONTAL
                    textView {
                        id = R.id.time
                        textSize = 16f
                        textColor = Color.BLACK
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
                        }.lparams(width = dip(0), height = wrapContent,weight = 4f)
                        textView {
                            id = R.id.scoreTeam
                            textSize = 16f
                            text = "VS"
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams(width = dip(0), height = wrapContent,weight = 2f) {
                            leftMargin = dip(10)
                            rightMargin = dip(10)
                        }
                        textView {
                            id = R.id.awayTeam
                            textSize = 16f
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams(width = dip(0), height = wrapContent,weight = 4f)
                    }
                }
            }
        }
    }
}