package com.thousand.bosch.global.utils

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object  RequestBodyHelper {
    internal fun <T> getRequestBodyText(item: T): RequestBody = RequestBody.create(
        MediaType.parse("text/plain"), item.toString()
    )

    internal fun getRequestBodyImage(item: File): RequestBody = RequestBody.create(
        MediaType.parse("image/png"), item
    )

    internal fun getRequestBodyImage(item: ByteArray): RequestBody = RequestBody.create(
        MediaType.parse("image/png"), item
    )

    internal fun getMultipartData(name: String, file: File?): MultipartBody.Part? {
        file?.apply {
            val filePart = getRequestBodyImage(this)

            return MultipartBody.Part.createFormData(
                name,
                this.name,
                filePart
            )
        }
        return null
    }

    internal fun getMultipartData(
        name: String,
        file_name: String,
        filePart: RequestBody?
    ): MultipartBody.Part? {
        filePart?.apply {
            return MultipartBody.Part.createFormData(
                name,
                file_name,
                filePart
            )
        }
        return null
    }


}