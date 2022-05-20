package com.example.imagerecognition.logic


import androidx.lifecycle.liveData
import com.example.imagerecognition.logic.model.AnimalResponse
import com.example.imagerecognition.logic.model.PlantResponse
import com.example.imagerecognition.logic.network.ImageRecognitionNetwork
import kotlinx.coroutines.Dispatchers

object Repository {

    fun getAnimalInfo(image: String) = liveData(Dispatchers.IO) {
        val result = try {
            val animalResponse = ImageRecognitionNetwork.getAnimalInfo(image)

            Result.success(animalResponse.result)

        } catch (e:Exception){
            Result.failure<List<AnimalResponse.Result>>(e)
        }
        emit(result)
    }

    fun getPlantInfo(image: String) = liveData(Dispatchers.IO) {
        val result = try {
            val plantResponse = ImageRecognitionNetwork.getPlantInfo(image)

            Result.success(plantResponse.result)

        } catch (e:Exception){
            Result.failure<List<PlantResponse.Result>>(e)
        }
        emit(result)
    }


}