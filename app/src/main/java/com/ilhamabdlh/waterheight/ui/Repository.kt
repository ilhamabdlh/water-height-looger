package com.ilhamabdlh.waterheight.ui

import com.ilhamabdlh.waterheight.data.ApiCreator
import com.ilhamabdlh.waterheight.data.Post
import com.ilhamabdlh.waterheight.data.service.GetHeightService
import retrofit2.Response
import retrofit2.awaitResponse

object Repository {
    suspend fun getHeight(): Response<Post> {
        return ApiCreator.retrofit
            .create(GetHeightService::class.java)
            .getHeight()
            .awaitResponse()
    }

    suspend fun getPost(): Response<Post> {
        return ApiCreator.retrofit
            .create(GetHeightService::class.java)
            .getPost()
            .awaitResponse()
    }
}