package com.example.imagerecognition.logic


import androidx.lifecycle.liveData
import com.example.imagerecognition.logic.model.AnimalResponse
import com.example.imagerecognition.logic.network.ImageRecognitionNetwork
import kotlinx.coroutines.Dispatchers

object Repository {

    fun getAnimalInfo(image: String, baikeNum: Int) = liveData(Dispatchers.IO) {
        val result = try {
            val animalResponse = ImageRecognitionNetwork.getAnimalInfo(image, baikeNum)

            Result.success(animalResponse.result)

        } catch (e:Exception){
            Result.failure<List<AnimalResponse.Result>>(e)
        }
        emit(result)
    }


}