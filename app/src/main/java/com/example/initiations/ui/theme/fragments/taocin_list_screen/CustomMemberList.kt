package com.example.initiations.ui.theme.fragments.taocin_list_screen
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.initiations.R
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.viewmodols.GoogleSheetsViewModel
import com.example.initiations.di.viewmodols.MainViewmodel
import com.example.initiations.util.AppConstant
import com.example.initiations.util.UiState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
 fun CustomMemberList(navController: NavController) {
    val viewModel: MainViewmodel = hiltViewModel()
    val sheetState by viewModel.memberListState.collectAsState()
    val searchText = remember { mutableStateOf("") }
    val context = LocalContext.current


    // Load data when composable is first composed
    LaunchedEffect(Unit) {
        viewModel.getAllMemberList()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {

        // --- SearchBar ---
        SearchBar(
            query = searchText.value,
            onQueryChange = { searchText.value = it },
            onSearch = {},
            active = false,
            onActiveChange = {},
            placeholder = { Text("Search by Name") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                if (searchText.value.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "Clear",
                        modifier = Modifier.clickable {
                            searchText.value = ""
                        }
                    )
                }
            }
        ) {}

        // --- UIState Handling ---
        when (sheetState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        (sheetState as UiState.Error).message ?: "Unknown error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            is UiState.Success -> {
                val allMembers =
                    (sheetState as UiState.Success<List<InitiationFiled>>).data ?: emptyList()

                // Apply search filter
                val filteredMembers = allMembers.filter {
                    it.personName.contains(searchText.value, ignoreCase = true)
                }

                if (filteredMembers.isEmpty()) {
                    Text("No results found", modifier = Modifier.padding(top = 16.dp))
                } else {
                    LazyColumn {
                        items(filteredMembers) { member ->
                            TaoCinCardDesign(
                                initiationFiled = member,
                                onItemClick = {
                                    // Navigate with identifier (you may need to pass ID or serialize the object)
//                                    navController.navigate("${AppConstant.FragmentTitles.MEMBER_DETAIL_SCREEN}/${member.personId}")
                                }
                            )
                        }
                    }
                }
            }

            UiState.Idle -> {
                // No-op or show a hint message
            }
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


