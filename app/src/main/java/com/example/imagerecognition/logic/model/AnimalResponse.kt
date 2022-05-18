package com.example.imagerecognition.logic.model

import com.google.gson.annotations.SerializedName

data class AnimalResponse(val result: List<Result>, @SerializedName("log_id")val logID: Double){

    data class Result(val score: String, val name: String, @SerializedName("baike_info") val baikeInfo: BaikeInfo)

    data class BaikeInfo(@SerializedName("baike_url") val baikeURL: String, @SerializedName("image_url") val imageURL: String, val description: String)

}
