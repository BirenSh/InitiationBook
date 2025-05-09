package com.example.initiations.ui.theme.fragments.initiation_form

data class InitiationFormState(
    var personName: String = "",
    var personAge: String = "0",
    var gender: String = "",
    var education: String = "",
    var fullAddress: String = "",
    var masterName: String = "",
    var introducerName: String = "",
    var guarantorName: String = "",
    var templeName: String = "",
    var initiationDate: String = "",
    var meritsFee: String = "100",
    var is2dayDharmaMeetingCompleted: Boolean = false
)
