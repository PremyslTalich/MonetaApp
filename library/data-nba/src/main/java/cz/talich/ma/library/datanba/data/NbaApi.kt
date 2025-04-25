package cz.talich.ma.library.datanba.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaApi {
    @GET("v1/players")
    suspend fun getPlayers(
        @Query("cursor") cursor: Int,
        @Query("per_page") pageSize: Int,
    ): Response<PlayersResponseDto>
}