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
import com.example.initiations.ui.theme.common_compose.CompletedAnimation
import com.example.initiations.util.AppConstant
import kotlinx.coroutines.delay

@Composable
fun FirstTimeSyncScreenCompose(navController: NavHostController) {
    val firstTimeSyncViewModel: FirstTimeSyncViewModel = hiltViewModel()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        CompletedAnimation(R.raw.sync_lottie)
        LaunchedEffect(this) {
            firstTimeSyncViewModel.getMembersFromFirebase()
            delay(10000L)
            navController.navigate(AppConstant.SerializeScreenName.TAOCHIN_LIST_SCREEN)
        }
    }
}