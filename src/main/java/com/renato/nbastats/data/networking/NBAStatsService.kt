package com.renato.nbastats.data.networking

import com.renato.nbastats.data.model.GameRecap
import com.renato.nbastats.data.model.Player
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NBAStatsService {

    @GET("/players")
    suspend fun getAllPlayers(
        @Query("query") query: String?
    ): Response<ArrayList<Player>>

    @GET("/player/{id}")
    suspend fun getPlayerById(
        @Path("id") query: Int
    ): Response<Player>

    @GET("/player")
    suspend fun getPlayersPaged(
        @Body body: PlayerPagedRequest
    ): Response<Player>

    @GET("/games/{tricode}")
    suspend fun getTeamGames(
        @Path("tricode") teamCode: String
    ): Response<List<GameRecap>>

    @GET("/games/date/{epochDate}")
    suspend fun getGamesForDate(
        @Path("epochDate") epochDate: Long
    ): Response<List<GameRecap>>

}

data class PlayerPagedRequest(
    val page: Int = 1,
    val pageSize: Int = 10
)