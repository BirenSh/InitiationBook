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
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.ui.theme.fragments.SplashScreenCompose
import com.example.initiations.ui.theme.fragments.UploadCompleteScreen
import com.example.initiations.ui.theme.fragments.first_time_sync_screen.FirstTimeSyncScreenCompose
import com.example.initiations.ui.theme.fragments.initiation_form.InitiationDetails
import com.example.initiations.ui.theme.fragments.login_screen.LoginScreenCompose
import com.example.initiations.ui.theme.fragments.taocin_detail_screen.MemberDetailScreen
import com.example.initiations.ui.theme.fragments.taocin_list_screen.MemberListScreen
import com.example.initiations.util.AppConstant
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
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
            composable(AppConstant.FragmentTitles.FIRST_TIME_SYNC_SCREEN) {
                FirstTimeSyncScreenCompose(navController)
            }
            composable(AppConstant.FragmentTitles.INITIATION_INSERTION){
                InitiationDetails(navController)
            }
            composable<AppConstant.SerializeScreenName.TAOCHIN_LIST_SCREEN>{
                MemberListScreen(navController)
            }

            composable<InitiationFiled>{
                val arg = it.toRoute<InitiationFiled>()
                MemberDetailScreen(arg)
            }
            composable(AppConstant.FragmentTitles.UPLOAD_COMPLETE_SCREEN) {
                UploadCompleteScreen(navController)
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
