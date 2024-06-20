package com.example.initiations.ui.theme.fragments

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.initiations.R
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.entities.Person
import com.example.initiations.di.viewmodols.MainViewmodel
import com.example.initiations.util.AppConstant

//@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaocinListScreenCompose(navController:NavController){
    val mainViewmodel:MainViewmodel = hiltViewModel()
    val memberlist = mainViewmodel.initiationMembers.value
    val addMember = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.orange_light)
                ),
                title = { Text(text = "Person Data") },
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
        content = {paddingValues ->
            Box(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()){

                LazyColumn {
//                     items(items = memberlist, itemContent = {
//                         TaoCinCardDesign(it)
//                     },
//                         )
                    items(memberlist){member ->
                        TaoCinCardDesign(
                            initiationFiled = member,
                            onItemClick = {
                                navController.navigate(Person("BirendraSH"))
                            // navigate to MemberDetail screen
                        })

                    }
                }

            }
        },

        bottomBar = {
            BottomBarComposeDesign()
        }

        )
}




@Composable
fun BottomBarComposeDesign() {
    BottomAppBar(
        containerColor = colorResource(id = R.color.orange_light),
        modifier = Modifier
            .fillMaxHeight(0.08f)
            .shadow(
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp), elevation = 2.dp
            )
    ) {
//                BottomBarComposeDesign()
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
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewUI(){
    BottomBarComposeDesign()

}