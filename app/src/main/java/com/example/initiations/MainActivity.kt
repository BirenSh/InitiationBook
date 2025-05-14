package com.example.initiations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import cafe.adriel.voyager.navigator.Navigator
import com.example.initiations.ui.theme.fragments.SplashScreenComposeScreen
import com.example.initiations.ui.theme.fragments.initiation_form.InitiationDetails

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
        Navigator(SplashScreenComposeScreen())
//        val navController = rememberNavController()
//        NavHost(navController = navController, startDestination = "SplashScreen"){
//            //splash screen
//            composable(AppConstant.FragmentTitles.SPLASH_SCREEN){
//                SplashScreenCompose(navController)
//            }
//            //login screen
//            composable(AppConstant.FragmentTitles.LOGIN_SCREEN){
//                LoginScreenCompose(navController)
//            }
//            composable(AppConstant.FragmentTitles.FIRST_TIME_SYNC_SCREEN) {
//                FirstTimeSyncScreenCompose(navController)
//            }
//            composable(AppConstant.FragmentTitles.INITIATION_INSERTION){
//                InitiationDetails(navController)
//            }
//            composable<AppConstant.SerializeScreenName.TAOCHIN_LIST_SCREEN>{
//                MemberListScreen(navController)
//            }
//
//            composable<InitiationFiled>{
//                val arg = it.toRoute<InitiationFiled>()
//                MemberDetailScreen(arg)
//            }
//            composable(AppConstant.FragmentTitles.UPLOAD_COMPLETE_SCREEN) {
//                UploadCompleteScreen(navController)
//            }
//        }
    }



}
