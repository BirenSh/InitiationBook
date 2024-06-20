package com.example.initiations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.initiations.di.entities.Person
import com.example.initiations.ui.theme.fragments.InitiationDetails
import com.example.initiations.ui.theme.fragments.LoginScreenCompose
import com.example.initiations.ui.theme.fragments.MemberDetailScreen
import com.example.initiations.ui.theme.fragments.SplashScreenCompose
import com.example.initiations.ui.theme.fragments.TaocinListScreenCompose
import com.example.initiations.util.AppConstant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent(){
            Navigation()
        }
    }

    @Composable
    private fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "SplashScreen"){
            //splash screen
            composable(AppConstant.FragmentTitles.SPLASH_SCREEN){
                SplashScreenCompose(navController)
            }
            //login screen
            composable(AppConstant.FragmentTitles.LOGIN_SCREEN){
                LoginScreenCompose(navController)
            }
            composable(AppConstant.FragmentTitles.INITIATION_INSERTION){
                InitiationDetails(navController)
            }
            composable<AppConstant.SerializeScreenName.TAOCHIN_LIST_SCREEN>{
                TaocinListScreenCompose(navController)
            }

            composable<Person>{
                val arg = it.toRoute<Person>()
                MemberDetailScreen(arg)
            }
        }
    }

    
    @Preview(showSystemUi = true)
    @Composable
    fun showView(){
        val navController = NavHostController(this)
        InitiationDetails(navController)
    }

}
