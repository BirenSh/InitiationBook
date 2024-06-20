package com.example.initiations.ui.theme.fragments

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.initiations.di.entities.InitiationFiled

//@Preview(showSystemUi = true)
@Composable
fun MemberDetailScreen(arg: InitiationFiled) {
    Text(text = "${arg.personAge} Details")
}