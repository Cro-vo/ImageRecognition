package com.example.imagerecognition.ui.animal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.imagerecognition.logic.Repository

class AnimalViewModel: ViewModel() {

    private val imageLiveData = MutableLiveData<String>()

    val animalLiveData = Transformations.switchMap(imageLiveData) { image ->
        Repository.getAnimalInfo(image)
    }

    fun getAnimalInfo(image: String) {
        imageLiveData.value = image
    }

}