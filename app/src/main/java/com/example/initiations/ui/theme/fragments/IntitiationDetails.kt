package com.example.initiations.ui.theme.fragments

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InitiationDetails(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text(text = "Person Data") },
            )
        },

        content = {paddingValue->
            Box(modifier = Modifier.padding(paddingValue)) {
                InitiationInputDataCompose(navHostController =  navController)
            }
        },

//        bottomBar = {
//            BottomAppBar(containerColor = Color.Yellow)
//            {
//                Text(
//                    text = "Botton bar",
//                )
//            }
//        }
    )


}