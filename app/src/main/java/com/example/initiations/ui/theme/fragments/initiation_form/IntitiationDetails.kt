package com.example.initiations.ui.theme.fragments.initiation_form

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.example.initiations.R
import com.example.initiations.ui.theme.fragments.scannerScreen.CameraScanScreen


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
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {

                        Text(text = "Person Data")
                        Icon(
                            imageVector = Icons.Default.DocumentScanner,
                            contentDescription = "Scan Form",
                            modifier = Modifier.clickable {
                                navigator?.push(CameraScanScreen())
//                                navigator?.push(
//                                    CameraScanScreen { data ->
//                                        println("data is : $data")
//                                        // Handle result here, or use ViewModel / shared state
//                                    }
//                                )
                            }
                        )

                    }
                    }
            )

        },

        content = {paddingValue->
            Box(modifier = Modifier.padding(paddingValue)) {
                InitiationInputDataCompose(navigator)
            }
        },

    )


}