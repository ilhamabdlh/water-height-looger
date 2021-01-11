package com.ilhamabdlh.waterheight.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCreator {
    var retrofit = createRetrofit()

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://7a7b667f71.dataplicity.io")
            .client(createDefaultClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createDefaultClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        return builder.build()
    }
}