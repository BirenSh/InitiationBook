package com.example.initiations.di.modules

import android.content.Context
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import com.example.initiations.BuildConfig


object GoogleSheetHelper {
    private const val RANGE = "Sheet1!A:D" // Adjust as needed
    private const val APPLICATION_NAME = "My Compose App"
    private const val SPREADSHEET_ID = BuildConfig.SHEET_ID



    fun getSheetsService(context: Context): Sheets {
        val inputStream = context.assets.open("initiationbook_739abab98d20.json")
        val credential = GoogleCredential.fromStream(inputStream)
            .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets"))

        return Sheets.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        ).setApplicationName(APPLICATION_NAME).build()
    }

     fun createRow(context: Context, values: List<Any>): SheetAppendResult {
        return try {
            val sheetsService = getSheetsService(context)
            val body = ValueRange().setValues(listOf(values))
            val response = sheetsService.spreadsheets().values()
                .append(SPREADSHEET_ID, RANGE, body)
                .setValueInputOption("RAW")
                .setInsertDataOption("INSERT_ROWS")
                .execute()

            SheetAppendResult(
                success = true,
                updatedCells = response.updates?.updatedCells,
                response = response
            )

        } catch (e: Exception) {
            SheetAppendResult(
                success = false,
                errorMessage = e.message
            )
        }
    }


     fun readSheet(context: Context, range: String = RANGE): List<List<Any>> {
        val sheetsService = getSheetsService(context)
        val response: ValueRange = sheetsService.spreadsheets().values()
            .get(SPREADSHEET_ID, range)
            .execute()
        return response.getValues() ?: listOf()
    }

     fun updateRow(context: Context, rowIndex: Int, values: List<Any>) {
        val sheetsService = getSheetsService(context)
        val range = "Sheet1!A${rowIndex + 1}:D${rowIndex + 1}" // Example for columns A to D
        val body = ValueRange().setValues(listOf(values))
        sheetsService.spreadsheets().values()
            .update(SPREADSHEET_ID, range, body)
            .setValueInputOption("RAW")
            .execute()
    }
}