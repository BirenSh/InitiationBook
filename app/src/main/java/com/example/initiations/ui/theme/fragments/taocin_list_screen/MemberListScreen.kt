package com.example.initiations.ui.theme.fragments.taocin_list_screen

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.initiations.R
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.viewmodols.MainViewmodel
import com.example.initiations.ui.theme.common_compose.CircularLoader
import com.example.initiations.ui.theme.fragments.taocin_list_screen.filter_list.FilterBottomSheet
import com.example.initiations.util.AppConstant
import java.util.Calendar

//@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberListScreen(navController:NavController){
    val mainViewmodel:MainViewmodel = hiltViewModel()
    val isLoading by mainViewmodel.isLoading.collectAsState()
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

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
                                mainViewmodel.getAllInitiationMembers()
                            }
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AppConstant.FragmentTitles.INITIATION_INSERTION)
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
                CustomMemberList(navController)
            }
        },

        bottomBar = {
            BottomBarComposeDesign()
        }

    )


    if (isLoading) {
        CircularLoader("Loading Taocin...")
    }

    BackHandler {
        (navController.context as ComponentActivity).finish()
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarComposeDesign() {

    val localContext = LocalContext.current
    BottomAppBar(
        containerColor = colorResource(id = R.color.orange_light),
        modifier = Modifier
            .fillMaxHeight(0.08f)
            .shadow(
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp), elevation = 2.dp
            )
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val is2DaysDmCompleteFilter = remember { mutableStateOf(false) }
            val isMaleFilter = remember { mutableStateOf(false) }
            val isFemaleFilter = remember { mutableStateOf(false) }
            val selectedDateFilter = remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
            val listOfYear = (2020..2088).toList()
//            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            val getIndexOfYear = listOfYear.indexOf(selectedDateFilter.value)
            val defaultSelectedYear = remember { mutableIntStateOf(getIndexOfYear) }


            FilterBottomSheet(
                is2DaysDmCompleteFilter = is2DaysDmCompleteFilter,
                isMaleFilter = isMaleFilter,
                isFemaleFilter = isFemaleFilter,
                selectedDateFilter = selectedDateFilter,
                selectedIndex = defaultSelectedYear,
                listOfYear = listOfYear
            )

            ButtonBarItems(
                Icons.Default.Person,
                itemName = "person",
                onItemClick = { Toast.makeText(localContext, "Filter", Toast.LENGTH_SHORT).show()}
            )
            ButtonBarItems(
                Icons.Default.FilterList,
                itemName = "Filter",
                onItemClick = { Toast.makeText(localContext, "Yet to Implement", Toast.LENGTH_SHORT).show()}
            )
            ButtonBarItems(
                Icons.Default.Person,
                itemName = "Person",
                onItemClick = { Toast.makeText(localContext, "Yet to Implement", Toast.LENGTH_SHORT).show()}
            )
        }
    }
}


@Composable
fun ButtonBarItems(imageVector:ImageVector, itemName:String, onItemClick: () -> Unit) {
    Box {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {
              onItemClick()
            }) {
            Icon(imageVector = imageVector, contentDescription = itemName)
            Text(text = "Filter")
        }
    }
}


@Composable
fun TaoCinCardDesign(initiationFiled: InitiationFiled, onItemClick:(InitiationFiled)->Unit ){
    Card(modifier = Modifier
        .padding(10.dp)
        .size(width = 500.dp, height = 70.dp)
        .clickable { onItemClick(initiationFiled) },
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(Color.White)
        ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.user_profile),
                contentDescription ="Person",
                modifier = Modifier
                    .size(70.dp)
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.02f))
            Column {
                Text(
                    text = initiationFiled.personName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = initiationFiled.templeName,
                    style = MaterialTheme.typography.labelMedium)
            }

            if (initiationFiled.is2DaysDharmaClassAttend){
                Box(
                    Modifier
                        .padding(start = 20.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.CenterEnd) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription ="Person",
                    )

                }
            }
        }
    }
}

@Composable
fun dataLoading() {
    CircularLoader("Data is loging...")
}



@Preview(showSystemUi = true)
@Composable
fun PreviewUI(){
    TaoCinCardDesign(InitiationFiled(1, personName = "birad", is2DaysDharmaClassAttend = true), onItemClick = {})

}