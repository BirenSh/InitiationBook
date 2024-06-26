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
        println("=========milis: $millisecond")
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        return simpleDateFormat.format(mili)
    }
}