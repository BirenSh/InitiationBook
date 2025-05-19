package com.example.initiations.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint

object ImageUtil {

    fun enhanceBitmap(original: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(original.width, original.height, original.config)
        val canvas = Canvas(output)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f) // grayscale
        val filter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = filter
        canvas.drawBitmap(original, 0f, 0f, paint)
        return output
    }


    fun parseOcrResult(rawText: String):  Map<String, String?> {
        val keyValueRegex = Regex("(name|address|phone)\\s*[:\\-]?\\s*(.*)", RegexOption.IGNORE_CASE)

        var name: String? = null
        var address: String? = null
        var phone: String? = null

        rawText.lines().forEach { line ->
            val match = keyValueRegex.find(line)
            if (match != null) {
                val key = match.groupValues[1].lowercase()
                val value = match.groupValues[2].trim()

                when (key) {
                    "name" -> name = value
                    "address" -> address = value
                    "phone" -> phone = value
                }
            }
        }
        return  mapOf(
            "name" to name,
            "address" to address,
            "phone" to phone
        )
    }
}