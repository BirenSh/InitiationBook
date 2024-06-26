package com.example.initiations.ui.theme.fragments.taocin_list_screen.filter_list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.initiations.R
import com.example.initiations.di.viewmodols.MainViewmodel
import com.example.initiations.ui.theme.fragments.taocin_list_screen.ButtonBarItems
import java.util.Calendar

@Preview(showSystemUi = true)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet() {
    val mainViewmodel: MainViewmodel = hiltViewModel()
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }
    val localContext = LocalContext.current



    ButtonBarItems(
        Icons.Default.FilterList,
        itemName = "Filter",
        onItemClick = {
            showBottomSheet.value = true
        }
    )
    if (showBottomSheet.value) {
        val is2DaysDmCompleteFilter = remember { mutableStateOf(false) }
        val isMaleFilter = remember { mutableStateOf(false) }
        val isFemaleFilter = remember { mutableStateOf(false) }
        val selectedDateFilter = remember {mutableStateOf(Calendar.getInstance().get(Calendar.YEAR))}
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = sheetState,
        ) {
            Box(
                Modifier
                    .fillMaxHeight(0.5f)
                    .padding(8.dp),) {
                Column(verticalArrangement = Arrangement.SpaceAround) {
                    // 2Days DM checkbox
                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = is2DaysDmCompleteFilter.value, onCheckedChange = {
                            if (it){
                                Toast.makeText(localContext, "Filter Apply ", Toast.LENGTH_SHORT).show()
                                // TODO: implement DB call based on filter
                            }
                            is2DaysDmCompleteFilter.value = !is2DaysDmCompleteFilter.value
                        })
                        Text(text = stringResource(id = R.string.dharma_meeting_2days))
                    }

                    // Gender Checkbox
                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = isMaleFilter.value, onCheckedChange = {
                            if (it){
                                Toast.makeText(localContext, "Filter Apply ", Toast.LENGTH_SHORT).show()
                                // TODO: implement DB call based on filter
                            }
                            isMaleFilter.value = !isMaleFilter.value
                        })
                        Text(text = stringResource(id = R.string.male))

                        Checkbox(checked = isFemaleFilter.value, onCheckedChange = {
                            if (it){
                                Toast.makeText(localContext, "Filter Apply ", Toast.LENGTH_SHORT).show()
                                // TODO: implement DB call based on filter
                            }
                            isFemaleFilter.value = !isFemaleFilter.value
                        })
                        Text(text = stringResource(id = R.string.female))
                    }

                    //Initiation Year based filter
                    Text(
                        text = "Select Year",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                    )
                    val listOfYear = (2020..2088).toList()
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                    val getIndexOfYear = listOfYear.indexOf(currentYear)
                    val selectedIndex = remember { mutableIntStateOf(getIndexOfYear) }
                    LazyRow(Modifier.padding(3.dp)) {
                        itemsIndexed(listOfYear){index,year->
                            val isSelected = selectedIndex.intValue == index
                            Button(
                                onClick = {
                                    selectedDateFilter.value = year
                                    selectedIndex.intValue = index
                                },
                                modifier = Modifier.padding(3.dp),
                                colors = if (isSelected){
                                    ButtonDefaults.buttonColors(colorResource(id = R.color.orange_light))
                                } else ButtonDefaults.buttonColors(Color.Gray),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(text =year.toString() )
                            }
                        }
                    }

                    // proceed to apply filter button
                    Button(
                        onClick = {
                            // call viewmodel with filter
                            val genderString =  if(isFemaleFilter.value && isMaleFilter.value) null else if (isMaleFilter.value) "Male" else if (isFemaleFilter.value) "Female" else null
                            mainViewmodel.getFilterMembers(genderString,selectedDateFilter.value,is2DaysDmCompleteFilter.value)
                            showBottomSheet.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Text(text = "Apply Filter")

                    }

                }
            }
        }
    }
}