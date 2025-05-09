package com.example.initiations.ui.theme.fragments.taocin_list_screen
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.viewmodols.GoogleSheetsViewModel
import com.example.initiations.di.viewmodols.MainViewmodel
import com.example.initiations.util.UiState

//@Preview(showSystemUi = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
 fun CustomMemberList(navController: NavController) {
     val mainViewmodel:MainViewmodel = hiltViewModel()
     val googleSheetVm:GoogleSheetsViewModel = hiltViewModel()
    val sheetState by googleSheetVm.memberState.collectAsState()
    val searchText = remember {
        mutableStateOf("")
    }

    val localContext = LocalContext.current

    LaunchedEffect(sheetState) {
        if (sheetState is UiState.Error) {
            Toast.makeText(
                localContext,
                (sheetState as UiState.Error).message ?: "Unknown error",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val memberList = when (sheetState) {
        is UiState.Success -> {
            (sheetState as UiState.Success<List<InitiationFiled>>).data
        }
        else -> emptyList()
    }

    if (memberList.isNullOrEmpty()){
        Toast.makeText(localContext, "No Data found", Toast.LENGTH_SHORT).show()
    }


    LaunchedEffect(Unit){
        googleSheetVm.getSheetData()
    }




//    val isSearching by mainViewmodel.isSearching.collectAsState()
    Box(modifier = Modifier
        .fillMaxSize()){
        Column(modifier = Modifier.padding(5.dp)) {
            SearchBar(
                query = searchText.value,
                onQueryChange = {
                    searchText.value = it
                    mainViewmodel.onSearchTextChange(searchText.value)
                },
                onSearch = { mainViewmodel.onSearchTextChange(searchText.value) } ,
                active =false,
                onActiveChange ={},
                placeholder = { Text(text = "Search by Name")},
                leadingIcon = { Icon(imageVector = Icons.Default.Search , contentDescription = "search icon")},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "search icon",
                        modifier = Modifier.clickable {
                            mainViewmodel.onSearchTextChange("")
                            searchText.value = ""
                        }
                    )
                },
            ) {

            }

            LazyColumn {
                items(memberList?: emptyList()){ member ->
                    TaoCinCardDesign(
                        initiationFiled = member,
                        onItemClick = {
                        navController.navigate(member)
                            // navigate to MemberDetail screen
                        })

                }
            }
        }
    }
}

