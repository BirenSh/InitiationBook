package com.example.initiations.ui.theme.fragments.filter_taochin

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class FilterTaochinScreen:Screen {
    @Composable
    override fun Content() {
       FilterTaochinList()
    }


    @Composable
    fun FilterTaochinList(modifier: Modifier = Modifier) {
        Text("Welcome to taochin filter screen")
    }
}