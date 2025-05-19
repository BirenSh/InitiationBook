package com.example.initiations.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import com.example.initiations.di.entities.InitiationFiled

object ImageUtil {
    fun enhanceBitmap(original: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(original.width, original.height, original.config)
        val canvas = Canvas(output)
        val paint = Paint()

        val contrast = 1.5f  // >1 to increase contrast
        val brightness = -20f // negative darkens, positive brightens

        val colorMatrix = ColorMatrix().apply {
            setSaturation(0f) // grayscale
            val contrastMatrix = floatArrayOf(
                contrast, 0f, 0f, 0f, brightness,
                0f, contrast, 0f, 0f, brightness,
                0f, 0f, contrast, 0f, brightness,
                0f, 0f, 0f, 1f, 0f
            )
            postConcat(ColorMatrix(contrastMatrix))
        }

        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(original, 0f, 0f, paint)

        return output
    }


    fun parseOcrResult(text: String): InitiationFiled {
        val lines = text.lines().map { it.trim() }

        fun findAfterKeyword(keyword: String): String? {
            val line = lines.find { it.contains(keyword, ignoreCase = true) } ?: return null
            val parts = line.split(keyword, ignoreCase = true)
            return if (parts.size > 1) parts[1].trim() else null
        }

        val name = findAfterKeyword("Name") ?: ""
        val age = findAfterKeyword("Age") ?: "0"
        val gender = when {
            lines.any { it.contains("Male", ignoreCase = true) } -> "Male"
            lines.any { it.contains("Female", ignoreCase = true) } -> "Female"
            else -> ""
        }
        val education = findAfterKeyword("Educational") ?: ""
        val address = findAfterKeyword("Address") ?: ""
        val phone = findAfterKeyword("Telephone") ?: ""
        val master = findAfterKeyword("Master") ?: ""
        val introducedBy = findAfterKeyword("Introduced By") ?: ""
        val guarantee = findAfterKeyword("Guarantee By") ?: ""
        val foThang = findAfterKeyword("Fo Thang") ?: "Hong Ci"
        val meritFee = findAfterKeyword("Merit Fee") ?: lines.find { it.contains("100") } ?: "100"

        return InitiationFiled(
            personId = "", // You can generate a UUID or timestamp if needed
            personName = name,
            personAge = age,
            gender = gender,
            education = education,
            fullAddress = address,
            contact = phone,
            masterName = master,
            introducerName = introducedBy,
            guarantorName = guarantee,
            templeName = foThang,
            initiationDate = "", // You may add date detection logic if needed
            meritFee = meritFee,
            is2DaysDharmaClassAttend = false,
            dharmaMeetingDate = ""
        )
    }

}