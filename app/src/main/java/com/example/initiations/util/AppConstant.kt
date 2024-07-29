package com.example.initiations.util

import kotlinx.serialization.Serializable

object AppConstant {

    object FragmentTitles{
        const val SPLASH_SCREEN = "SplashScreen"
        const val LOGIN_SCREEN = "LoginScreen"
        const val INITIATION_INSERTION = "InitiationInsertion"
        const val UPLOAD_COMPLETE_SCREEN = "UploadCompleteScreen"
        const val MEMBER_DETAIL_SCREEN = "MemberDetailScreen"

    }
    object SerializeScreenName{
        @Serializable
        object TAOCHIN_LIST_SCREEN
    }

    const val LOGIN_USER_NAME = "Birendra"
    const val LOGIN_PASSWORD = "1234"
}