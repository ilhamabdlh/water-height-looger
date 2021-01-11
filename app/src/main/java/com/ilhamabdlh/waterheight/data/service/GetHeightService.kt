package com.ilhamabdlh.waterheight.data.service

import com.ilhamabdlh.waterheight.data.Post
import retrofit2.Call
import retrofit2.http.GET

interface GetHeightService {
    @GET("log/read")
    fun getHeight(): Call<Post>

    @GET("log/read")
    fun getPost(): Call<Post>
}