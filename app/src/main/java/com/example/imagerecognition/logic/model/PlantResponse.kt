package com.example.imagerecognition.logic.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlantResponse(val result: List<Result>, @SerializedName("log_id")val logID: Double): Parcelable{

    @Parcelize
    data class Result(val score: String, val name: String, @SerializedName("baike_info") val baikeInfo: BaikeInfo): Parcelable

    @Parcelize
    data class BaikeInfo(@SerializedName("baike_url") val baikeURL: String, @SerializedName("image_url") val imageURL: String, val description: String): Parcelable

}
