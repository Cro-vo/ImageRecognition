package com.example.imagerecognition

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException

object Utils {
    /*
     * bitmap转base64
     * */
    fun bitmapToBase64(bitmap: Bitmap?): String? {
        var result: String? = null
        var baos: ByteArrayOutputStream? = null
        try {
            if (bitmap != null) {
                baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                baos.flush()
                baos.close()
                val bitmapBytes = baos.toByteArray()
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (baos != null) {
                    baos.flush()
                    baos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return result
    }
    /*end*/


    private fun imageZoom(bitMap:Bitmap): Bitmap {
        var newBitmap:Bitmap = bitMap
        //图片允许最大空间 单位：KB
        val maxSize = 64
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        val baos = ByteArrayOutputStream()
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        //将字节换成KB
        val mid = b.size / 1024.toDouble()
        //判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            val i = mid / maxSize
            //开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            newBitmap = zoomImage(
                bitMap, bitMap.getWidth() / Math.sqrt(i),
                bitMap.getHeight() / Math.sqrt(i)
            )
        }
        return newBitmap
    }


    public fun zoomImage(bgimage: Bitmap?, newWidth: Double, newHeight: Double): Bitmap {
        // 获取这个图片的宽和高
        val width = bgimage!!.width.toFloat()
        val height = bgimage!!.height.toFloat()
        // 创建操作图片用的matrix对象
        val matrix = Matrix()
        // 计算宽高缩放率
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(
            bgimage, 0, 0, width.toInt(),
            height.toInt(), matrix, true
        )
    }

    public fun compressBitmap(bitmap: Bitmap): Bitmap {
        // 将base64限制在30000以内
        var base64 = bitmapToBase64(bitmap)
        var len = base64!!.length
        var bitmap = bitmap
        while (len > 30000) {
            bitmap = zoomImage(bitmap, bitmap!!.width / 1.2, bitmap!!.height / 1.2)
            len = bitmapToBase64(bitmap)!!.length
        }
        return bitmap
    }

}