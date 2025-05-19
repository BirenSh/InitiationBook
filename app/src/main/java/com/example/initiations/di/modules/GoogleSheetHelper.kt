package com.example.initiations.di.modules

import android.content.Context
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import com.example.initiations.BuildConfig
import com.example.initiations.di.entities.InitiationFiled


object GoogleSheetHelper {
    private const val RANGE = "Sheet1!A:N" // Adjust as needed
    private const val APPLICATION_NAME = "My Compose App"
    private const val SPREADSHEET_ID = BuildConfig.SHEET_ID



    fun getSheetsService(context: Context): Sheets {
        val inputStream = context.assets.open("initiationbook-c5a1ba2ced4b.json")
        val credential = GoogleCredential.fromStream(inputStream)
            .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets"))

        return Sheets.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        ).setApplicationName(APPLICATION_NAME).build()
    }

     fun createRow(context: Context, dataMap: Map<String, Any>): SheetAppendResult<InitiationFiled> {
        return try {
            val sheetsService = getSheetsService(context)
            // Fetch headers from the first row
            val headers = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute()
                .getValues()
                ?.firstOrNull()
                ?.map { it.toString() }
                ?: emptyList()

            // Build row based on header order
            val rowValues = headers.map { header -> dataMap[header] ?: "" }

            val body = ValueRange().setValues(listOf(rowValues))
            val response = sheetsService.spreadsheets().values()
                .append(SPREADSHEET_ID, RANGE, body)
                .setValueInputOption("RAW")
                .setInsertDataOption("INSERT_ROWS")
                .execute()

            SheetAppendResult(
                success = true,
                updatedCells = response.updates?.updatedCells,
                response = response,
                data = null
            )

        } catch (e: Exception) {
            println("====Exp: $e")
            SheetAppendResult(
                success = false,
                errorMessage = e.message
            )
        }
    }


    fun readSheetAsObjects(context: Context): SheetAppendResult<InitiationFiled> {
        return try {
            val sheetsService = getSheetsService(context)
            val response: ValueRange = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute()

            val values = response.getValues() ?: return SheetAppendResult(success = true)

            val data = values.drop(1).mapNotNull { row ->
                try {
                    InitiationFiled(
                        personId = row.getOrNull(0)?.toString() ?: "",
                        personName = row.getOrNull(1)?.toString() ?: "",
                        personAge = row.getOrNull(2)?.toString() ?: "",
                        gender = row.getOrNull(3)?.toString() ?: "",
                        education = row.getOrNull(4)?.toString() ?: "",
                        fullAddress = row.getOrNull(5)?.toString() ?: "",
                        masterName = row.getOrNull(6)?.toString() ?: "",
                        introducerName = row.getOrNull(7)?.toString() ?: "",
                        guarantorName = row.getOrNull(8)?.toString() ?: "",
                        templeName = row.getOrNull(9)?.toString() ?: "",
                        initiationDate = row.getOrNull(10)?.toString() ?: "",
                        meritFee = row.getOrNull(11)?.toString() ?: "",
                        is2DaysDharmaClassAttend = row.getOrNull(12)?.toString()?.toBooleanStrictOrNull() ?: false,
                        dharmaMeetingDate = row.getOrNull(13)?.toString() ?: ""
                    )
                } catch (e: Exception) {
                    return SheetAppendResult(success = false, data = null, errorMessage = e.message)
                }
            }

            SheetAppendResult(success = true, data = data)

        } catch (e: Exception) {
            SheetAppendResult(success = false, data = null, errorMessage = e.message)
        }
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