package com.example.yusriyusron.matchscheduler.views

import com.example.yusriyusron.matchscheduler.api.ApiRepository
import com.example.yusriyusron.matchscheduler.api.TheSportDBApi
import com.example.yusriyusron.matchscheduler.models.MatchResponse
import com.example.yusriyusron.matchscheduler.models.TeamResponse
import com.example.yusriyusron.matchscheduler.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(
    private val view: MainView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getNextMatch() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getNextMatch()),
                MatchResponse::class.java
            )
            uiThread {
                view.hideLoading()
                view.showMatchList(data.events)
            }
        }
    }

    fun getLastMatch() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getLastMatch()),
                MatchResponse::class.java
            )
            uiThread {
                view.hideLoading()
                view.showMatchList(data.events)
            }
        }
    }

    fun getTeamList() {
        view.showLoading()

        GlobalScope.async(context.main) {
            val data = async {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeams()),
                    TeamResponse::class.java
                )
            }
            view.hideLoading()
            view.showTeamList(data.await().teams)
        }
    }
}