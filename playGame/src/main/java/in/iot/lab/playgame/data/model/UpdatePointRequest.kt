package `in`.iot.lab.playgame.data.model

import com.google.gson.annotations.SerializedName

data class UpdatePointRequest(
    @SerializedName("score")
    val score: Int,
    @SerializedName("hintId")
    val hintId: String
)
