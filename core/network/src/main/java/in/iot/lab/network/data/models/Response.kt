package `in`.iot.lab.network.data.models

data class Response<T>(
    val status: Int,
    val data: T
)
