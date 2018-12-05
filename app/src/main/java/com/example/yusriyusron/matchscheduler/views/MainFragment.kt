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
import com.example.yusriyusron.matchscheduler.adapter.MainAdapter
import com.example.yusriyusron.matchscheduler.api.ApiRepository
import com.example.yusriyusron.matchscheduler.models.Match
import com.google.gson.Gson
import org.jetbrains.anko.*
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
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter

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

        loadMatchList()
        swipeRefreshLayout.onRefresh {
            loadMatchList()
        }

        adapter = MainAdapter(match, this)
        val layoutManager = LinearLayoutManager(context)
        listMatch.adapter = adapter
        listMatch.layoutManager = layoutManager
        listMatch.addItemDecoration(
            DividerItemDecoration(
                listMatch.context, layoutManager.orientation
            )
        )
        return view
    }

    fun loadMatchList() {
        if (type.equals("next")) {
            presenter.getNextMatch()
        } else {
            presenter.getLastMatch()
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


    override fun click(match: Match) {
        val intent = Intent(context, DetailView::class.java)
        intent.putExtra("match", match)
        context.startActivity(intent)
    }
}
