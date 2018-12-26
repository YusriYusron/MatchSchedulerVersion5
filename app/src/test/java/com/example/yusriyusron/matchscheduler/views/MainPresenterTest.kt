package com.example.yusriyusron.matchscheduler.views

import com.example.yusriyusron.matchscheduler.TestContextProvider
import com.example.yusriyusron.matchscheduler.api.ApiRepository
import com.example.yusriyusron.matchscheduler.models.Match
import com.example.yusriyusron.matchscheduler.models.MatchResponse
import com.example.yusriyusron.matchscheduler.models.Team
import com.example.yusriyusron.matchscheduler.models.TeamResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock
    private lateinit var view: MainView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getNextMatch() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)

        val urlApi = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"

        GlobalScope.launch {
            `when`(
                gson.fromJson(apiRepository.doRequest(urlApi), MatchResponse::class.java)
            ).thenReturn(response)
            presenter.getNextMatch()

            verify(view).showLoading()
            verify(view).showMatchList(match)
            verify(view).hideLoading()
        }
    }

    @Test
    fun getLastMatch() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)

        val urlApi = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"

        GlobalScope.launch {
            `when`(
                gson.fromJson(apiRepository.doRequest(urlApi), MatchResponse::class.java)
            ).thenReturn(response)
            presenter.getLastMatch()

            verify(view).showLoading()
            verify(view).showMatchList(match)
            verify(view).hideLoading()
        }
    }

    @Test
    fun getTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)

        val urlApi = "https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id=4328"

        GlobalScope.launch {
            `when`(
                gson.fromJson(apiRepository.doRequest(urlApi), TeamResponse::class.java)
            ).thenReturn(response)
            presenter.getTeamList()
            verify(view).showLoading()
            verify(view).showTeamList(teams)
            verify(view).hideLoading()
        }
    }
}