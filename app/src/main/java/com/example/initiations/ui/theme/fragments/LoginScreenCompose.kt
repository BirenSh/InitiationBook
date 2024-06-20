package com.example.initiations.ui.theme.fragments

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.initiations.R
import com.example.initiations.ui.theme.common_compose.CustomElevatedButton
import com.example.initiations.ui.theme.common_compose.OutlinedTextFieldCompose
import com.example.initiations.util.AppConstant

@Composable
fun LoginScreenCompose(navController: NavHostController) {
    val localContext = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    username = AppConstant.LOGIN_USER_NAME
    password = AppConstant.LOGIN_PASSWORD

    Box(modifier = Modifier
        .background(colorResource(id = R.color.orange_light))
        .fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.BottomEnd){

        Card(
            shape = RoundedCornerShape(topStart = 100.dp),
            colors = CardDefaults.cardColors( colorResource(id = R.color.white )),
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(14.dp)
            ){

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly

            ) {
                 OutlinedTextFieldCompose(
                    placeHolder = "Enter User Name",
                    leadingIcon = Icons.Default.Person,
                    text = username,
                    onTextChanged = { username = it }
                )
                OutlinedTextFieldCompose(
                    placeHolder = "Enter Password",
                    leadingIcon = Icons.Default.Lock,
                    keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Number),
                    text = password,
                    onTextChanged = { password = it }

                )

                CustomElevatedButton(
                    onClick = {
                        if (username.equals(AppConstant.LOGIN_USER_NAME.trim(), true) && password.equals(
                                AppConstant.LOGIN_PASSWORD.trim(),true
                            )
                        ) {
                            Toast.makeText(localContext, "Login Successful", Toast.LENGTH_SHORT)
                                .show()
                            navController.navigate(AppConstant.SerializeScreenName.TAOCHIN_LIST_SCREEN)
                        } else Toast.makeText(localContext, "Failed login", Toast.LENGTH_SHORT)
                            .show()
                    },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    text = "Log in"
                )
            }
        }

    }
}

