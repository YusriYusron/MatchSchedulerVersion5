package com.example.yusriyusron.matchscheduler.views

import android.content.Context
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.yusriyusron.matchscheduler.adapter.FavoriteAdapter
import com.example.yusriyusron.matchscheduler.adapter.MainAdapter
import com.example.yusriyusron.matchscheduler.api.ApiRepository
import com.example.yusriyusron.matchscheduler.database.Favorite
import com.example.yusriyusron.matchscheduler.database.database
import com.example.yusriyusron.matchscheduler.models.Match
import com.example.yusriyusron.matchscheduler.models.Team
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainFragment(private val type: String) : AnkoComponent<ViewGroup>, MainView,
    com.example.yusriyusron.matchscheduler.adapter.Handler {

    private lateinit var context: Context
    private lateinit var listMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var match: MutableList<Match> = mutableListOf()
    private var teams: MutableList<Team> = mutableListOf()
    private var favorites: MutableList<Favorite> = mutableListOf()
    private var key: MutableList<String?> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun createView(ui: AnkoContext<ViewGroup>): View {
        this.context = ui.ctx
        val view = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                swipeRefreshLayout = swipeRefreshLayout {
                    setColorSchemeResources(
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                    )

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listMatch = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                        }

                        progressBar = progressBar {
                            visibility = View.INVISIBLE
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)
        presenter.getTeamList()

        if(type.equals("favorite")){
            favoriteAdapter = FavoriteAdapter(favorites, this)
            listMatch.adapter = favoriteAdapter
            swipeRefreshLayout.onRefresh { showFavorites() }
            showFavorites()
        }else{
            adapter = MainAdapter(match, this)
            listMatch.adapter = adapter
            loadMatchList()
            swipeRefreshLayout.onRefresh { loadMatchList() }
        }

        val layoutManager = LinearLayoutManager(context)
        listMatch.layoutManager = layoutManager
        listMatch.addItemDecoration(
            DividerItemDecoration(
                listMatch.context, layoutManager.orientation
            )
        )
        return view
    }

    fun showFavorites(){
        favorites.clear()
        context.database?.use {
            swipeRefreshLayout.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            favoriteAdapter.notifyDataSetChanged()
        }
    }

    fun loadMatchList() {
        if (type.equals("next")) {
            presenter.getNextMatch()
        } else if(type.equals("last")){
            presenter.getLastMatch()
        }else{
            showFavorites()
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showMatchList(data: List<Match>) {
        swipeRefreshLayout.isRefreshing = false
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showTeamList(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        key.clear()
        teams.forEach {
            key.add(it.teamName)
        }
    }

    override fun getTeam(idTeam: String?): Team {
        return teams.get(key.indexOf(idTeam))
    }

    override fun click(match: Favorite) {
        val intent = Intent(context, DetailView::class.java)
        intent.putExtra("match", match.matchId)
        intent.putExtra("homeTeam", match.homeTeam)
        intent.putExtra("awayTeam", match.awayTeam)
        context.startActivity(intent)
    }

    override fun click(match: Match) {
        val intent = Intent(context, DetailView::class.java)
        intent.putExtra("match", match.eventId)
        intent.putExtra("homeTeam", match.idHomeTeam)
        intent.putExtra("awayTeam", match.idAwayTeam)
        context.startActivity(intent)
    }
}
