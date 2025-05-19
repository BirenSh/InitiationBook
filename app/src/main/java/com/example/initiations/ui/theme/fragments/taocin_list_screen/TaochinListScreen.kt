package com.example.initiations.ui.theme.fragments.taocin_list_screen

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.example.initiations.R
import com.example.initiations.di.viewmodols.MainViewmodel
import com.example.initiations.ui.theme.fragments.filter_taochin.FilterTaochinScreen
import com.example.initiations.ui.theme.fragments.initiation_form.InitiationDetailScreen
import com.example.initiations.ui.theme.fragments.taocin_detail_screen.MemberDetailScreen
import com.example.initiations.util.UiState


//@Preview(showSystemUi = true)

class TaochinListScreen:Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        TaochinListCompose(navigator)
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaochinListCompose(navController: Navigator?){
    val localContext = LocalContext.current
    val viewModel: MainViewmodel = hiltViewModel()
    val memberListState by viewModel.memberListState.collectAsState()

    // Initial data load
    LaunchedEffect(Unit) {
        viewModel.getAllMemberList()
    }

    Scaffold(

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.orange_light)
                ),

                title = {
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)) {
                        Text(text = "Person Data")
                        Icon(
                            imageVector = Icons.Default.RestartAlt,
                            contentDescription = "Reload",
                            modifier = Modifier.clickable {
                                viewModel.reSyncSheetData()
                            }
                        )
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Reload",
                            modifier = Modifier.clickable {
                                navController?.push(FilterTaochinScreen())
                            }
                        )


                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController?.push(InitiationDetailScreen())
                },
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                },
                containerColor = colorResource(id = R.color.orange_light)
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                CustomMemberList(
                    sheetState = memberListState,
                    onItemClick = { member ->
                        navController?.push(MemberDetailScreen(member))
                    }
                )
            }
        },

        bottomBar = {
        }

    )

    BackHandler {
        (localContext as? Activity)?.finish()
    }
}