package com.example.imagerecognition.logic.service

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.imagerecognition.ImageRecognitionApplication
import com.example.imagerecognition.ImageRecognitionApplication.Companion.context
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class DownloadService : Service() {

    private val mBinder = DownloadBinder()

    class DownloadBinder: Binder() {

        fun startDownload(context: Context, url: String){

            thread {
                val file = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()
                val bitmap = BitmapFactory.decodeFile(file.path)

                runBlocking {
                    // 兼容29版本上下
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        saveImage29(bitmap, context)
                    } else {
                        saveImage(bitmap, context)
                    }
                }
//                Log.d("test", bitmap.toString())

            }

        }


        private fun saveImage(toBitmap: Bitmap, context: Context) {
            val insertImage = MediaStore.Images.Media.insertImage(
                context.contentResolver, toBitmap, "ImageRecognition Download", "ImageRecognition保存的图片"
            )
            if (insertImage.isNotEmpty()) {
                Toast.makeText(context, "图片保存成功！-${insertImage}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "图片保存失败！}", Toast.LENGTH_SHORT).show()
            }
        }

        // 保存图片
        private suspend fun saveImage29(toBitmap: Bitmap, context: Context) {
            //开始一个新的进程执行保存图片的操作
            withContext(Dispatchers.IO) {

                val insertUri = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues()
                ) ?: kotlin.run {
                    showSaveToast(context, "保存失败！")
                    return@withContext
                }
                //使用use可以自动关闭流
                context.contentResolver.openOutputStream(insertUri).use {
                    if (toBitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)) {
                        val intent = Intent("com.example.imagerecognition.MSG_BROADCAST")
                        intent.setPackage(context.packageName)
                        context.sendBroadcast(intent)
//                        showSaveToast(context,"保存成功！")
//                        Log.d("test","ok")
                    } else {
                        showSaveToast(context, "保存失败！")
//                        Log.d("test","fail")
                    }
                }
            }
        }
        private fun showSaveToast(context: Context, showMsg: String) =
            MainScope().launch {
                Toast.makeText(context, showMsg, Toast.LENGTH_SHORT).show()
            }


//        fun saveMediaToStorage(context: Context,bitmap: Bitmap) {
//            //Generating a file name
//            val filename = "${System.currentTimeMillis()}.jpg"
//
//            //Output stream
//            var fos: OutputStream? = null
//
//            //For devices running android >= Q
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                //getting the contentResolver
//                context?.contentResolver?.also { resolver ->
//
//                    //Content resolver will process the contentvalues
//                    val contentValues = ContentValues().apply {
//
//                        //putting file information in content values
//                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
//                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
//                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//                    }
//
//                    //Inserting the contentValues to contentResolver and getting the Uri
//                    val imageUri: Uri? =
//                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//
//                    //Opening an outputstream with the Uri that we got
//                    fos = imageUri?.let { resolver.openOutputStream(it) }
//                }
//            } else {
//                //These for devices running on android < Q
//                //So I don't think an explanation is needed here
//                val imagesDir =
//                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                val image = File(imagesDir, filename)
//                fos = FileOutputStream(image)
//            }
//
//            fos?.use {
//                //Finally writing the bitmap to the output stream that we opened
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
////                Log.d("test","save success.")
//            }
//        }




    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }





}