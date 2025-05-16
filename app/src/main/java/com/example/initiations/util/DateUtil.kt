package com.example.initiations.util

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat


object DateUtil {

    @SuppressLint("SimpleDateFormat")
    fun convertMillisecondToDate(millisecond: Long? = System.currentTimeMillis()): String {
        var mili = System.currentTimeMillis()
        if (millisecond == null) {
            mili
        } else mili = millisecond
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        return simpleDateFormat.format(mili)
    }

    fun generatePersonId(name: String): String {
        val formatter = java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault())
        val datePart = formatter.format(java.util.Date())
        val namePart = name.take(2).uppercase()
        return datePart + namePart
    }
}