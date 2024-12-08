package com.example.moltitube.Network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YoutubeClient() {

    val queryParam = mapOf(
        "type" to "video",
        "key" to "AIzaSyDuW47_2h0sCXZJmEWVREbTJgrGHbmOsXc",
        "part" to "snippet",
        "maxResults" to "10"
    )


    val okhttpClient =
        OkHttpClient.Builder().addInterceptor(YoutubeApiKeyInterceptor(queryParam)).build()
    val retrofit =
        Retrofit.Builder().baseUrl("https://youtube.googleapis.com/youtube/v3/").client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()

    val youtubeService: YoutubeService by lazy { retrofit.create(YoutubeService::class.java) }
}