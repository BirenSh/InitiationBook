package com.example.initiations.di.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import javax.annotation.Nonnull

@Serializable
@Entity(tableName = "initiationPerson")
data class InitiationFiled(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0 ,
    @Nonnull
    val personName :String = "",
    @Nonnull
    val personAge:Int = 0,
    @Nonnull
    val gender:String = "",
    @Nonnull
    val education:String = "",
    @Nonnull
    val fullAddress:String = "",
    @Nonnull
    val masterName:String = "",
    @Nonnull
    val introducerName:String = "",
    @Nonnull
    val guarantorName:String = "",
    @Nonnull
    val templeName:String = "",
    @Nonnull
    val initiationDate:String = "",
    @Nonnull
    val meritFee:String = "",
    @Nonnull
    val is2DaysDharmaClassAttend:Boolean = false,
    @Nonnull
    val dharmaMeetingDate:String = ""

)
