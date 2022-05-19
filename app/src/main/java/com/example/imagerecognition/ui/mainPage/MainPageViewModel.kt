package com.example.imagerecognition.ui.mainPage

import androidx.lifecycle.ViewModel
import com.example.imagerecognition.R
import com.example.imagerecognition.logic.model.FunctionObject

class MainPageViewModel: ViewModel() {

    val functionList = listOf(
        FunctionObject("动物识别", R.mipmap.animal_icon),
    FunctionObject("植物识别", R.mipmap.plant_icon)
    )



}