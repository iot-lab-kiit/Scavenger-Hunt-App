package `in`.iot.lab.network.data.models

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("state")
    val status: Int,
    @SerializedName("data")
    val data: T
)
