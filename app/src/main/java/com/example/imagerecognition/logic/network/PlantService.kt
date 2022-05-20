package com.example.imagerecognition.logic.network

import com.example.imagerecognition.ImageRecognitionApplication
import com.example.imagerecognition.logic.model.AnimalResponse
import com.example.imagerecognition.logic.model.PlantResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface PlantService {


    @POST("rest/2.0/image-classify/v1/plant?access_token=${ImageRecognitionApplication.TOKEN}&baike_num=6")
    fun getPlantInfo(@Query("image") image: String): Call<PlantResponse>


}