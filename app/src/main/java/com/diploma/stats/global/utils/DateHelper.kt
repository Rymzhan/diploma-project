package com.diploma.stats.global.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun convertOneFormatToAnother(
        dateStr: String?,
        parserFormat: String,
        formatterFormat: String
    ): String {

        dateStr?.apply {
            val parser = SimpleDateFormat(parserFormat, Locale.forLanguageTag("kk"))
            val formatter = SimpleDateFormat(formatterFormat, Locale.forLanguageTag("kk"))

            return formatter.format(parser.parse(dateStr))
        }
        return ""
    }

}