package com.example.initiations.ui.theme.common_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun CompletedAnimation(
    lottieIcon:Int,
    animationSpeed:Float = 1.0f
) {
    // TODO: rename this func
    val completeLottieComposition = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(
        lottieIcon
    ))

    val preloaderProgress by animateLottieCompositionAsState(
        completeLottieComposition.value,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = animationSpeed
    )

    LottieAnimation(
        composition = completeLottieComposition.value,
        progress = preloaderProgress,
        modifier = Modifier
    )


}