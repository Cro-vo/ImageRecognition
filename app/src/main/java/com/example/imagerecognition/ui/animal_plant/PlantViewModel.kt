package com.example.imagerecognition.ui.animal_plant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.imagerecognition.logic.Repository

class PlantViewModel: ViewModel() {

    private val imageLiveData = MutableLiveData<String>()

    val plantLiveData = Transformations.switchMap(imageLiveData) { image ->
        Repository.getPlantInfo(image)
    }

    fun getPlantInfo(image: String) {
        imageLiveData.value = image
    }

}