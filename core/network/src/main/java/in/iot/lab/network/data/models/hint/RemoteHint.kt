package `in`.iot.lab.network.data.models.hint

import com.google.gson.annotations.SerializedName

data class RemoteHint(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("question")
    val question: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("answer")
    val answer: String? = null,
    @SerializedName("campus")
    val campus: Int? = null,
    @SerializedName("imageURL")
    val image: String? = null
)