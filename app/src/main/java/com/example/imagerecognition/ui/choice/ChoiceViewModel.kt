package com.example.imagerecognition.ui.choice

import androidx.lifecycle.ViewModel
import com.example.imagerecognition.R
import com.example.imagerecognition.logic.model.ChoiceObject
import com.example.imagerecognition.logic.model.FunctionObject

class ChoiceViewModel: ViewModel() {

    public val choiceList = listOf(
        ChoiceObject("使用相机拍照", R.mipmap.takephoto_icon2),
        ChoiceObject("从相册获取", R.mipmap.album_icon2)
    )

}