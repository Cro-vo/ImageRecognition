package com.example.imagerecognition.logic.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object ImageRecognitionNetwork {

    fun hello(){
        println("hello ")
    }

    private val animalService = ServiceCreator.create<AnimalService>()

    suspend fun getAnimalInfo(image: String) = animalService.getAnimalInfo(image).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
//                        Log.d("base64","msgOK:" + response.toString())
//                        Log.d("base64","msgOKbody:" + body.toString())
                    }
                    else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
//                        Log.d("base64","msgNO:" + response.toString())
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}