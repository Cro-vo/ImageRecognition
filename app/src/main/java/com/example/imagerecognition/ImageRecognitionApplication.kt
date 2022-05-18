package com.example.imagerecognition

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class ImageRecognitionApplication: Application() {

    companion object {

        const val TOKEN = "24.4adeb5eee8f296db424d78285c43b5b6.2592000.1655462461.282335-26263670"

        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}