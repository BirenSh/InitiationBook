package com.example.initiations.ui.theme.fragments.first_time_sync_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.initiations.R
import com.example.initiations.di.viewmodols.FirstTimeSyncViewModel
import com.example.initiations.di.viewmodols.GoogleSheetsViewModel
import com.example.initiations.ui.theme.common_compose.CompletedAnimation
import com.example.initiations.util.AppConstant
import kotlinx.coroutines.delay

@Composable
fun FirstTimeSyncScreenCompose(navController: NavHostController) {
    val firstTimeSyncViewModel: FirstTimeSyncViewModel = hiltViewModel()


    LaunchedEffect(Unit){
        firstTimeSyncViewModel.getSheetData()
    }

    // Navigate after splash delay
    LaunchedEffect(Unit) {
        delay(500L) // Adjust duration if animation is longer
        navController.navigate(AppConstant.SerializeScreenName.TAOCHIN_LIST_SCREEN) {
            popUpTo(AppConstant.FragmentTitles.FIRST_TIME_SYNC_SCREEN) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        CompletedAnimation(R.raw.sync_lottie)
    }
}