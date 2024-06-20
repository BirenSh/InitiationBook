package com.example.initiations.di.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "initiationPerson")
data class InitiationFiled(
    @PrimaryKey(autoGenerate = true)
    val id:Int ,
    val personName :String = "",
    val personAge:Int = 0,
    val gender:String = "",
    val education:String = "",
    val fullAddress:String = "",
    val masterName:String = "",
    val introducerName:String = "",
    val guarantorName:String = "",
    val templeName:String = ""

)
