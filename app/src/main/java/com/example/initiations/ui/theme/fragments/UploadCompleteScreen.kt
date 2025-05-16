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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.popUntil
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.example.initiations.R
import com.example.initiations.di.viewmodols.MainViewmodel
import com.example.initiations.ui.theme.common_compose.CompletedAnimation
import com.example.initiations.ui.theme.fragments.taocin_list_screen.TaochinListScreen
import com.example.initiations.util.AppConstant
import kotlinx.coroutines.delay

class UploadCompleteScreen:Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        UploadCompleteCompose(navigator)
    }

}
@Composable
fun UploadCompleteCompose(navController: Navigator?) {
    val mainViewmodel:MainViewmodel = hiltViewModel()


    Box(
        modifier = Modifier
            .padding(40.dp)
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center,
    ) {
        CompletedAnimation(
            lottieIcon =  R.raw.completed,
            animationSpeed = 2.0f
        )
        LaunchedEffect(this) {
            delay(500L)
            navController?.popUntil { screen ->
                screen is TaochinListScreen
            }
        }
    }
}