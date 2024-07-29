package com.example.initiations.ui.theme.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.initiations.di.viewmodols.MainViewmodel
import com.example.initiations.ui.theme.common_compose.CompletedAnimation
import com.example.initiations.util.AppConstant
import kotlinx.coroutines.delay


@Composable
fun UploadCompleteScreen(navController: NavController) {
    val mainViewmodel:MainViewmodel = hiltViewModel()

    Box(
        modifier = Modifier
            .padding(40.dp)
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center,
    ) {
        CompletedAnimation()
        LaunchedEffect(this) {
            delay(2000L)
            navController.popBackStack( AppConstant.SerializeScreenName.TAOCHIN_LIST_SCREEN,false)
        }
    }
}