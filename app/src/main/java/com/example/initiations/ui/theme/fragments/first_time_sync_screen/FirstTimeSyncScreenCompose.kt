package com.example.initiations.ui.theme.fragments.first_time_sync_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.example.initiations.R
import com.example.initiations.di.viewmodols.FirstTimeSyncViewModel
import com.example.initiations.ui.theme.common_compose.CompletedAnimation
import com.example.initiations.ui.theme.fragments.taocin_list_screen.TaochinListScreen
import com.example.initiations.util.AppConstant
import kotlinx.coroutines.delay

class FirstTimeSyncScreen :Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        FirstTimeSyncScreenCompose(navigator)
    }

}
@Composable
fun FirstTimeSyncScreenCompose(navController: Navigator?) {
    val firstTimeSyncViewModel: FirstTimeSyncViewModel = hiltViewModel()


    LaunchedEffect(Unit){
        firstTimeSyncViewModel.getSheetData()
    }

    // Navigate after splash delay
    LaunchedEffect(Unit) {
        delay(5000L) // Adjust duration if animation is longer
        navController?.replaceAll(TaochinListScreen())
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