package com.example.initiations.ui.theme.common_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.initiations.R

@Composable
fun CompletedAnimation() {
    val completeLottieComposition = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(
        R.raw.completed
    ))

    val preloaderProgress by animateLottieCompositionAsState(
        completeLottieComposition.value,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    LottieAnimation(
        composition = completeLottieComposition.value,
        progress = preloaderProgress,
        modifier = Modifier
    )


}