package com.example.initiations.di.modules

import com.google.api.services.sheets.v4.model.AppendValuesResponse

data class SheetAppendResult(
    val success: Boolean,
    val updatedCells: Int? = null,
    val response: AppendValuesResponse? = null,
    val errorMessage: String? = null
)
