package com.example.initiations.ui.theme.fragments.initiation_form

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.example.initiations.R


class InitiationDetailScreen :Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        InitiationDetails(navigator)
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InitiationDetails(navigator: Navigator?) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = colorResource(id = R.color.orange_light)
                ),
                title = { Text(text = "Person Data") },
            )
        },

        content = {paddingValue->
            Box(modifier = Modifier.padding(paddingValue)) {
                InitiationInputDataCompose(navigator)
            }
        },

    )


}