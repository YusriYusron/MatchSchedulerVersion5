package com.example.yusriyusron.matchscheduler.views

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Bundle
import android.support.design.R.attr.colorAccent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.yusriyusron.matchscheduler.R
import com.example.yusriyusron.matchscheduler.api.ApiRepository
import com.example.yusriyusron.matchscheduler.api.TheSportDBApi
import com.example.yusriyusron.matchscheduler.database.Favorite
import com.example.yusriyusron.matchscheduler.database.database
import com.example.yusriyusron.matchscheduler.models.Match
import com.example.yusriyusron.matchscheduler.models.MatchResponse
import com.example.yusriyusron.matchscheduler.models.Team
import com.example.yusriyusron.matchscheduler.models.TeamResponse
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import com.example.yusriyusron.matchscheduler.R.menu.detail_menu
import com.example.yusriyusron.matchscheduler.R.drawable.ic_add_to_favorites
import com.example.yusriyusron.matchscheduler.R.drawable.ic_added_to_favorites
import com.example.yusriyusron.matchscheduler.R.id.add_to_favorite
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DetailView : AppCompatActivity() {
    private lateinit var homeTeamBadge: ImageView
    private lateinit var awayTeamBadge: ImageView
    private lateinit var homeTeamName: TextView
    private lateinit var awayTeamName: TextView
    private lateinit var homeTeamScore: TextView
    private lateinit var awayTeamScore: TextView
    private lateinit var homeRedCard: TextView
    private lateinit var awayRedCard: TextView
    private lateinit var homeYellowCard: TextView
    private lateinit var awayYellowCard: TextView
    private lateinit var homeTeamGoals: TextView
    private lateinit var awayTeamGoals: TextView
    private lateinit var homeTeamShoot: TextView
    private lateinit var awayTeamShoot: TextView
    private lateinit var homeTeamKeeper: TextView
    private lateinit var awayTeamKeeper: TextView
    private lateinit var homeTeamDefense: TextView
    private lateinit var awayTeamDefense: TextView
    private lateinit var homeTeamMidfield: TextView
    private lateinit var awayTeamMidfield: TextView
    private lateinit var homeTeamForward: TextView
    private lateinit var awayTeamForward: TextView
    private lateinit var homeTeamSubtitutes: TextView
    private lateinit var awayTeamSubtitutes: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var homeTeam : Team
    private lateinit var awayTeam : Team
    private lateinit var match : Match
    private lateinit var id : String
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            padding = dip(16)
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    android.R.color.holo_green_light,
                    android.R.color.holo_red_light,
                    android.R.color.background_dark)
                scrollView {
                    lparams(width = matchParent, height = matchParent)
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        lparams(width = matchParent, height = matchParent)
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            linearLayout {
                                gravity = Gravity.CENTER
                                rightPadding = dip(10)
                                lparams(width = dip(0), height = wrapContent, weight = 4f)
                                orientation = LinearLayout.VERTICAL

                                // Home Team Badge
                                homeTeamBadge = imageView().lparams(width = dip(80), height = dip(80))
                                homeTeamName = textView {
                                    textSize = 16f
                                    gravity = Gravity.CENTER
                                }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = dip(0), height = wrapContent, weight = 3f)
                                orientation = LinearLayout.HORIZONTAL

                                // Home Team Score
                                homeTeamScore = textView {
                                    gravity = Gravity.CENTER
                                    padding = dip(10)
                                    textSize = 30f
                                }.lparams(width = dip(0), height = wrapContent, weight = 1f)
                                textView {
                                    gravity = Gravity.CENTER
                                    text = " VS "
                                }.lparams(width = dip(0), height = wrapContent, weight = 1f)

                                //Away Team Score
                                awayTeamScore = textView {
                                    gravity = Gravity.CENTER
                                    padding = dip(10)
                                    textSize = 30f
                                }.lparams(width = dip(0), height = wrapContent, weight = 1f)
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                rightPadding = dip(10)
                                lparams(width = dip(0), height = wrapContent, weight = 4f)
                                orientation = LinearLayout.VERTICAL

                                // Away Team Badge
                                awayTeamBadge = imageView().lparams(width = dip(80), height = dip(80))
                                awayTeamName = textView {
                                    textSize = 16f
                                    gravity = Gravity.CENTER
                                }
                            }
                        }
                        linearLayout {
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL

                            // Home Team Goals
                            homeTeamGoals = textView {
                                gravity = Gravity.START
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                            textView {
                                text = "Goals"
                                gravity = Gravity.CENTER
                                textSize = 16f
                            }.lparams(width = dip(0), height = wrapContent, weight = 1f)

                            // Away Teasm Goals
                            awayTeamGoals = textView {
                                gravity = Gravity.END
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                        }
                        linearLayout {
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL

                            // Home Team Shoot
                            homeTeamShoot = textView {
                                gravity = Gravity.CENTER
                                textSize = 16f
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                            textView {
                                text = "Shoots"
                                gravity = Gravity.CENTER
                                textSize = 16f
                                topPadding = 50
                            }.lparams(width = dip(0), height = wrapContent, weight = 1f)

                            // Away Team Shoot
                            awayTeamShoot = textView {
                                gravity = Gravity.CENTER
                                textSize = 16f
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                        }
                        linearLayout {
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL

                            // Home Team Shoot
                            homeRedCard = textView {
                                gravity = Gravity.CENTER
                                textSize = 16f
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                            textView {
                                text = "Red Card"
                                gravity = Gravity.CENTER
                                textSize = 16f
                                topPadding = 50
                            }.lparams(width = dip(0), height = wrapContent, weight = 1f)

                            // Away Team Shoot
                            awayRedCard = textView {
                                gravity = Gravity.CENTER
                                textSize = 16f
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                        }
                        linearLayout {
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL

                            // Home Team Shoot
                            homeYellowCard = textView {
                                gravity = Gravity.START
                                textSize = 16f
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                            textView {
                                text = "Yellow Card"
                                gravity = Gravity.CENTER
                                textSize = 16f
                                topPadding = 50
                            }.lparams(width = dip(0), height = wrapContent, weight = 1f)

                            // Away Team Shoot
                            awayYellowCard = textView {
                                gravity = Gravity.END
                                textSize = 16f
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                        }
                        textView {
                            text = "Lineups"
                            gravity = Gravity.CENTER_HORIZONTAL
                            textSize = 20f
                            topPadding = 50
                            bottomPadding = 50
                            textColor = Color.BLACK
                        }.lparams(width = matchParent, height = wrapContent)
                        linearLayout {
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL

                            // Home Goal Keeper
                            homeTeamKeeper = textView {
                                gravity = Gravity.START
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                            textView {
                                text = "Goal Keeper"
                                gravity = Gravity.CENTER
                                textSize = 16f
                                topPadding = 50
                            }.lparams(width = dip(0), height = wrapContent, weight = 1f)

                            // Away Goal Keeper
                            awayTeamKeeper = textView {
                                gravity = Gravity.END
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                        }
                        linearLayout {
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL

                            //Home Defense
                            homeTeamDefense = textView {
                                gravity = Gravity.START
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                            textView {
                                text = "Defense"
                                gravity = Gravity.CENTER
                                textSize = 16f
                                topPadding = 50
                            }.lparams(width = dip(0), height = wrapContent, weight = 1f)

                            // Away Defense
                            awayTeamDefense = textView {
                                gravity = Gravity.END
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                        }
                        linearLayout {
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL

                            // Home Team Midfield
                            homeTeamMidfield = textView {
                                gravity = Gravity.START
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                            textView {
                                text = "Midfield"
                                gravity = Gravity.CENTER
                                textSize = 16f
                                topPadding = 50
                            }.lparams(width = dip(0), height = wrapContent, weight = 1f)

                            // Away Team Midfield
                            awayTeamMidfield = textView {
                                gravity = Gravity.END
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                        }
                        linearLayout {
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL

                            //Home Team Forward
                            homeTeamForward = textView {
                                gravity = Gravity.START
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                            textView {
                                text = "Forward"
                                gravity = Gravity.CENTER
                                textSize = 16f
                                topPadding = 50
                            }.lparams(width = dip(0), height = wrapContent, weight = 1f)

                            // Away Team Forward
                            awayTeamForward = textView {
                                gravity = Gravity.END
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                        }
                        linearLayout {
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL

                            //Home Team Subtitutes
                            homeTeamSubtitutes = textView {
                                gravity = Gravity.START
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                            textView {
                                text = "Subtitutes"
                                gravity = Gravity.CENTER
                                textSize = 16f
                                topPadding = 50
                            }.lparams(width = dip(0), height = wrapContent, weight = 1f)

                            // Away Team Subtitutes
                            awayTeamSubtitutes = textView {
                                gravity = Gravity.END
                            }.lparams(width = dip(0), height = wrapContent, weight = 2f)
                        }
                    }
                }
            }
        }

        val intent = intent
        id = intent.getStringExtra("match")
        val idHomeTeam = intent.getStringExtra("homeTeam")
        val idAwayTeam = intent.getStringExtra("awayTeam")

        loadMatch(id)
        loadATeam(idHomeTeam,0)
        loadATeam(idAwayTeam,1)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setToFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            if (match == null) return
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                    Favorite.MATCH_ID to match.eventId,
                    Favorite.EVENT_NAME to match.eventName,
                    Favorite.DATE_EVENT to match.dateEvent,
                    Favorite.HOME_SCORE to match.homeScore,
                    Favorite.AWAY_SCORE to match.awayScore,
                    Favorite.HOME_TEAM to match.idHomeTeam,
                    Favorite.AWAY_TEAM to match.idAwayTeam)
            }
            swipeRefresh.snackbar("Added to Favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(MATCH_ID = {id})",
                    "id" to id)
            }
            swipeRefresh.snackbar("Remove from Favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE).whereArgs("(MATCH_ID = {id})","id" to id)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()){
                isFavorite = true
                setToFavorite()
            }
        }
    }

    private fun setToFavorite(){
        if(isFavorite){
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        }else{
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
        }
    }

    fun loadDetailMatch(){
        favoriteState()
        awayTeamName.text = match.awayTeam
        homeTeamName.text = match.homeTeam

        if (match != null) {
            homeTeamGoals.text = match.homeGoalDetail
            awayTeamGoals.text = match.awayGoalDetail

            homeTeamScore.text = match.homeScore
            awayTeamScore.text = match.awayScore

            homeTeamShoot.text = match.homeShots
            awayTeamShoot.text = match.awayShots

            homeRedCard.text = match.homeRedCard
            awayRedCard.text = match.awayRedCard

            homeYellowCard.text = match.homeYellowCard
            awayYellowCard.text = match.awayYellowCard

            homeTeamKeeper.text = match.homeGoalkeeper
            awayTeamKeeper.text = match.awayGoalkeeper

            homeTeamDefense.text = match.homeDefense
            awayTeamDefense.text = match.awayDefense

            homeTeamMidfield.text = match.homeMidfield
            awayTeamMidfield.text = match.awayMidfield

            homeTeamForward.text = match.homeForward
            awayTeamForward.text = match.awayForward

            homeTeamSubtitutes.text = match.homeSubstitutes
            awayTeamSubtitutes.text = match.awaySubstitutes
        }
    }

    fun loadATeam(idTeam: String?, type: Int) {
        val request = ApiRepository()
        val gson = Gson()
        doAsync {
            val data = gson.fromJson(
                request.doRequest(TheSportDBApi.getATeam(idTeam)),
                TeamResponse::class.java
            )
            uiThread {
                var imageView : ImageView
                var team : Team
//                try {
                    if(type == 0){
                        homeTeam = data.teams.get(0)
                        team = homeTeam
                        imageView = homeTeamBadge
                    }else{
                        awayTeam = data.teams.get(0)
                        team = awayTeam
                        imageView = awayTeamBadge
                    }
                    Glide.with(applicationContext).load(team.teamBadge).into(imageView)
//                }catch (e:NullPointerException){
//
//                }
            }
        }
    }

    fun loadMatch(idMatch: String){
        val gson = Gson()
        val request = ApiRepository()

        doAsync {
            val data = gson.fromJson(request
                .doRequest(TheSportDBApi.getDetailMatch(idMatch)),
                MatchResponse::class.java
            )
            uiThread {
                match = data.events.get(0)
                loadDetailMatch()
            }
        }
    }
}