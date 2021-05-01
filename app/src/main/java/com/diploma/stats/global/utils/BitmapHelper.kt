package com.diploma.stats.global.utils

import android.graphics.Bitmap
import android.util.Log
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream

object BitmapHelper {

    fun getFileDataFromBitmap(fieldName: String, bitmap: Bitmap?): MultipartBody.Part? {
        if(bitmap != null){
            Log.i("bitmap","bitmap not null")
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)

            val filePart =
                RequestBodyHelper.getRequestBodyImage(byteArrayOutputStream.toByteArray())

            return RequestBodyHelper.getMultipartData(
                fieldName,
                "${System.currentTimeMillis()}.png",
                filePart
            )
        }
        return null
    }
}