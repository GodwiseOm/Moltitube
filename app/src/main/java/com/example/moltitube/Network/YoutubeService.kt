package com.example.moltitube.Network

import com.example.moltitube.Model.YotubeSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {

    @GET("search")
    suspend fun searchYoutube(@Query("q") searchQuery: String): Response<YotubeSearchResponse>

}