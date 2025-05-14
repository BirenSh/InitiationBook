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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.example.initiations.R
import com.example.initiations.ui.theme.fragments.login_screen.LoginScreen
import com.example.initiations.ui.theme.fragments.login_screen.LoginScreenCompose
import kotlinx.coroutines.delay

class SplashScreenComposeScreen :Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        SplashScreenCompose(navigator)
    }

}
@Composable
fun SplashScreenCompose(navController: Navigator?) {
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
        navController?.push(LoginScreen())
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