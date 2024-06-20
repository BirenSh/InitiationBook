package com.example.initiations.ui.theme.fragments

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.initiations.di.entities.Person

//@Preview(showSystemUi = true)
@Composable
fun MemberDetailScreen(arg: Person) {
    Text(text = "${arg.name} Details")
}