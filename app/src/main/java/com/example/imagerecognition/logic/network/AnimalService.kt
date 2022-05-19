package com.example.imagerecognition.logic.network

import com.example.imagerecognition.ImageRecognitionApplication
import com.example.imagerecognition.logic.model.AnimalResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AnimalService {


    @POST("rest/2.0/image-classify/v1/animal?access_token=${ImageRecognitionApplication.TOKEN}&baike_num=6")
    fun getAnimalInfo(@Query("imgUrl") image: String): Call<AnimalResponse>


}