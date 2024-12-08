package com.example.moltitube.Network

import okhttp3.Interceptor
import okhttp3.Response

class YoutubeApiKeyInterceptor (private val queryParams:Map<String,String>):Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
       val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val urlBuilder = originalUrl.newBuilder()

        queryParams.forEach {
            urlBuilder.addQueryParameter(it.key,it.value)
           }
        val newUrl = urlBuilder.build()
        val newRequest = originalRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)

    }
}