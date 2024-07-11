package com.example.initiations.ui.theme.fragments

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.initiations.R
import com.example.initiations.util.AppConstant
import kotlinx.coroutines.delay

@Composable
fun SplashScreenCompose(navController: NavHostController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(targetValue = 0.8f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(10f).getInterpolation(it)
                }))
                    delay(3000L)
        navController.navigate(AppConstant.FragmentTitles.LOGIN_SCREEN)
    }
    Image(
        painter = painterResource(id =R.drawable.kotlin ),
        contentDescription = "MCT logo",
        modifier = Modifier.fillMaxSize()
            .scale(scale.value),
        alignment = Alignment.Center
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewCompose(){

    Image(
        painter = painterResource(id =R.drawable.mct_logo ) ,
        contentDescription = "MCT logo",
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.Center
    )
}