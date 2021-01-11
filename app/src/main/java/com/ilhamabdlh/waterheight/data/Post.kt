package com.ilhamabdlh.waterheight.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Post(
    @SerializedName("ID")
    var id: Long,
    var name: String,
    var address: String = "JL Rawamangun Selatan, Jakarta Timur",
    @SerializedName("Height")
    var height: Int,
    var isEnabled: Boolean
) : Serializable