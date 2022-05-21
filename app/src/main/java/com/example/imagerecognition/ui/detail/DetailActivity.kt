package com.example.imagerecognition.ui.detail

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.imagerecognition.ImageRecognitionApplication
import com.example.imagerecognition.databinding.ActivityDetailBinding
import com.example.imagerecognition.logic.model.AnimalResponse
import com.example.imagerecognition.logic.model.PlantResponse
import com.example.imagerecognition.logic.service.DownloadService
import com.example.imagerecognition.ui.mainPage.MainActivity

class DetailActivity : AppCompatActivity() {

    // 存放网络图片url
    lateinit var imgUrl: String

    lateinit var downloadBinder: DownloadService.DownloadBinder

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            downloadBinder = p1 as DownloadService.DownloadBinder
            downloadBinder.startDownload(this@DetailActivity, imgUrl)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val function = intent.getIntExtra("function", 1)

        if (function == MainActivity.ANIMAL){
            val result = intent.getParcelableExtra<AnimalResponse.Result>("result")!!
            Glide.with(this).load(result!!.baikeInfo.imageURL).into(binding.imageView)
            binding.name.text = result.name
            binding.score.text = "相似度：${result.score}"
            binding.description.text = result.baikeInfo.description

            imgUrl = result.baikeInfo.imageURL

        } else if (function == MainActivity.PLANT){
//            Log.d("plant", "here")
            val result = intent.getParcelableExtra<PlantResponse.Result>("result")
            Glide.with(this).load(result!!.baikeInfo.imageURL).into(binding.imageView)
            binding.name.text = result.name
            binding.score.text = "相似度：${result.score}"
            binding.description.text = result.baikeInfo.description

            imgUrl = result.baikeInfo.imageURL

        }


        binding.button.setOnClickListener {
            requestPermission()

            val intent = Intent(this, DownloadService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)


        }
//        Log.d("result", "result in  activity ${result}")

    }


    /**
     * request permission for the application
     */
    private fun requestPermission() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        } else {
//            Log.d("test", "-----------------permission ok")
        }
    }

}