package com.example.imagerecognition.ui.choice

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.imagerecognition.MainActivity
import com.example.imagerecognition.Utils
import com.example.imagerecognition.databinding.ActivityChoiceBinding
import com.example.imagerecognition.ui.animal.AnimalViewModel
import java.io.File
import kotlin.math.max

class ChoiceActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(AnimalViewModel::class.java) }

    companion object {
        val TAKE_PHOTO: Int = 1
        val FROM_ALBUM: Int = 2
    }

    lateinit var imgUri: Uri
    lateinit var outputImg: File


    lateinit var binding: ActivityChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.photoButton.setOnClickListener {
            outputImg = File(this.externalCacheDir, "output_image.jpg")
            if (outputImg.exists()){
                outputImg.delete()
            }
            outputImg.createNewFile()
            imgUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                FileProvider.getUriForFile(this, "com.example.imagerecognition.fileprovider", outputImg)
            } else {
                Uri.fromFile(outputImg)
            }
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)

            startActivityForResult(intent, TAKE_PHOTO)

        }

        binding.albumButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, FROM_ALBUM)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK){
                    // 成功拍照
                    var bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imgUri))
                    // 服务器接收base64的最大长度为30000
                    bitmap = bitmap?.let { Utils.compressBitmap(it) }
                    val base64 = Utils.bitmapToBase64(bitmap)
                    if (base64 != null) {
                        getInfo(base64)
                    }

                }
            }
            FROM_ALBUM -> {
                if (resultCode == Activity.RESULT_OK && data != null){
                    // 选择相册
                    data.data?.let { uri ->
                        var bitmap = getBitmapFromUri(uri)
                        // 服务器接收base64的最大长度为30000
                        bitmap = bitmap?.let { Utils.compressBitmap(it) }

                        val base64 = Utils.bitmapToBase64(bitmap)
                        if (base64 != null) {
                            getInfo(base64)
                        }
                    }

                }
            }

        }

    }


    private fun getInfo(base64: String){
        val function = intent.getIntExtra("function", 1)
        if (function == MainActivity.ANIMAL){
            viewModel.getAnimalInfo(base64)
            Log.d("base64", base64.length.toString())
            viewModel.animalLiveData.observe(this, Observer {
                val results = it.getOrNull()
                Log.d("base_results",results.toString())
            })

        } else if (function == MainActivity.PLANT) {

        }

    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver
        .openFileDescriptor(uri, "r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }

}