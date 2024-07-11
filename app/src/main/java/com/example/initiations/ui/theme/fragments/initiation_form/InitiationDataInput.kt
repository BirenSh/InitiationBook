package com.example.initiations.ui.theme.fragments.initiation_form

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.initiations.R
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.viewmodols.MainViewmodel
import com.example.initiations.ui.theme.common_compose.CustomAlertDialog
import com.example.initiations.ui.theme.common_compose.CustomElevatedButton
import com.example.initiations.ui.theme.common_compose.DynamicSelectTextField
import com.example.initiations.ui.theme.common_compose.OutlinedTextFieldCompose
import com.example.initiations.util.DateUtil
import java.util.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitiationInputDataCompose(viewmodel: MainViewmodel = hiltViewModel(), navHostController: NavHostController){
    val personName = remember { mutableStateOf("") }
    val personAge = remember { mutableStateOf(0) }
    val education = remember { mutableStateOf("") }
    val fullAddress = remember { mutableStateOf("") }
    val masterName = remember { mutableStateOf("") }
    val introducerName = remember { mutableStateOf("") }
    val guarantorName = remember { mutableStateOf("") }
    val templeName = remember { mutableStateOf("") }
    val meritsFee = remember { mutableStateOf("100") }
    val is2dayDharmaMeetingCompleted = remember { mutableStateOf(false) }


    val selectedValue = remember { mutableStateOf("") }
    val initiationDate = remember { mutableStateOf("") }

    val openDialogBox = remember {
        mutableStateOf(false)
    }

    val localContext = LocalContext.current

    Column(
        modifier = Modifier
            .padding(5.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        InputCompose(
            personName = personName,
            personAge = personAge,
            selectedValue = selectedValue,
            education = education,
            fullAddress = fullAddress,
            masterName = masterName,
            introducerName = introducerName,
            guarantorName = guarantorName,
            templeName = templeName,
            initiationDate = initiationDate,
            meritsFee = meritsFee,
            is2DaysDharmaCompleted = is2dayDharmaMeetingCompleted

        )

        val dummyInitiationData = InitiationFiled(
            id = Random().nextInt(),
            personName =personName.value,
            personAge = personAge.value,
            gender = selectedValue.value,
            education = education.value,
            fullAddress =fullAddress.value,
            masterName = masterName.value,
            introducerName = introducerName.value,
            guarantorName = guarantorName.value,
            templeName = templeName.value,
            initiationDate = initiationDate.value,
            meritFee = meritsFee.value?:"100",
            is2DaysDharmaClassAttend =  is2dayDharmaMeetingCompleted.value?:false
        )

        val validation = personName.value.isNotEmpty() && personAge.value.toString().isNotEmpty() && selectedValue.value.isNotEmpty()
            && education.value.isNotEmpty() && fullAddress.value.isNotEmpty() && masterName.value.isNotEmpty()
            && introducerName.value.isNotEmpty() && guarantorName.value.isNotEmpty() && templeName.value.isNotEmpty()


        CustomElevatedButton(
            text = stringResource(id = R.string.proceed),
            onClick = {
                if (validation){
                    openDialogBox.value = true
                }else{
                    Toast.makeText(localContext, "All Filed are mandatory", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        )
        if (openDialogBox.value){
           CustomAlertDialog(
               onDismissRequest = { openDialogBox.value = false },
               onConfirmation = {
                   openDialogBox.value = false
                   viewmodel.insertInitiationDetails(dummyInitiationData)
                   Toast.makeText(localContext, "Initiation Successfully Added", Toast.LENGTH_SHORT).show()
               },
               title = "Adding New Member",
               text = "These detail are important to be input correctly, Please confirm "
           )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputCompose(
    personName: MutableState<String>,
    selectedValue: MutableState<String>,
    education: MutableState<String>,
    fullAddress: MutableState<String>,
    masterName: MutableState<String>,
    introducerName: MutableState<String>,
    guarantorName: MutableState<String>,
    templeName: MutableState<String>,
    initiationDate: MutableState<String>,
    meritsFee: MutableState<String>,
    personAge: MutableState<Int>,
    is2DaysDharmaCompleted: MutableState<Boolean>
) {

    OutlinedTextFieldCompose(
        placeHolder = stringResource(id = R.string.person_name),
        leadingIcon = Icons.Default.Person,
        text = personName.value,
        onTextChanged = { personName.value = it }, // TODO: need to add mutable value
        modifier = Modifier.fillMaxWidth(1f)
    )

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextFieldCompose(
            placeHolder = stringResource(id = R.string.age),
            leadingIcon = Icons.Filled.Numbers,
            text = personAge.value.toString(),
            onTextChanged = { personAge.value = it.toInt()}, // TODO: need to add mutable value
            modifier = Modifier.weight(1f),
            keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier
            .weight(0.2f)
            .weight(0.2f)
            .background(Color.Yellow))


        DynamicSelectTextField(
            selectedValue = selectedValue.value,
            label = stringResource(id = R.string.gender),
            options = listOf(
                stringResource(id = R.string.male),
                stringResource(id = R.string.female)
            ),
            onValueChangedEvent = { selectedValue.value = it },
            modifier = Modifier.weight(1f)
        )

    }

    OutlinedTextFieldCompose(
        leadingIcon = Icons.Filled.Edit,
        text = education.value,
        placeHolder = stringResource(id = R.string.education),
        onTextChanged = { education.value = it}, // TODO: need to add mutable value
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextFieldCompose(
        leadingIcon = Icons.Default.Edit,
        text = fullAddress.value,
        placeHolder = stringResource(id = R.string.full_address),
        onTextChanged = { fullAddress.value = it }, // TODO: need to add mutable value
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextFieldCompose(
        leadingIcon = Icons.Default.Person,
        text = masterName.value,
        placeHolder = stringResource(id = R.string.master_name),
        onTextChanged = {masterName.value = it}, // TODO: need to add mutable value
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextFieldCompose(
        leadingIcon = Icons.Default.Person,
        text = introducerName.value,
        placeHolder = stringResource(id = R.string.introducer_name),
        onTextChanged = {introducerName.value = it }, // TODO: need to add mutable value
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextFieldCompose(
        leadingIcon = Icons.Default.Person,
        text = guarantorName.value,
        placeHolder = stringResource(id = R.string.guarantor_name),
        onTextChanged = {guarantorName.value  = it}, // TODO: need to add mutable value
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextFieldCompose(
        leadingIcon = Icons.Default.Edit,
        text = templeName.value,
        placeHolder = stringResource(id = R.string.temple_name),
        onTextChanged = {templeName.value  = it }, // TODO: need to add mutable value
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextFieldCompose(
        leadingIcon = Icons.Default.Edit,
        text = meritsFee.value,
        placeHolder = stringResource(id = R.string.merits_fee),
        onTextChanged = {meritsFee.value  = it }, // TODO: need to add mutable value
        modifier = Modifier.fillMaxWidth(),
        keyBoardOption = KeyboardOptions(keyboardType =  KeyboardType.Number)
    )
    //date picker
    val showDialogBox = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val datePickerToFormat = DateUtil.convertMillisecondToDate(datePickerState.selectedDateMillis)
    initiationDate.value = datePickerToFormat
    Text(
        text = datePickerToFormat,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable(onClick = { showDialogBox.value = true })
            .background(Color.Transparent, shape = RoundedCornerShape(6.dp))
            .border(
                border = BorderStroke(1.dp, color = Color.Gray), shape = RoundedCornerShape(6.dp)
            )
            .padding(15.dp)
    )
    if (showDialogBox.value) {
        DatePickerDialog(
            onDismissRequest = {
                showDialogBox.value = false
            },
            confirmButton = {
                Button(onClick = { showDialogBox.value = false }) {
                    Text(text = "Ok")
                }
            }, dismissButton = {
                Button(onClick = { showDialogBox.value = false }) {
                    Text(text = "Cancell")
                }
            },

        ) {
            androidx.compose.material3.DatePicker(
                state = datePickerState,
                showModeToggle = true,
                colors = DatePickerDefaults.colors()
            )
        }
    }
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Checkbox(checked = is2DaysDharmaCompleted.value, onCheckedChange = { is2DaysDharmaCompleted.value = it})
        Text(text = "2 Day Dharma meeting Attended")
    }

}

@Preview(showSystemUi = true)
@Composable
fun DatePicker(){
    Text(
        text = "sdfsdsd",
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(6.dp))
            .border(
                border = BorderStroke(1.dp, color = Color.Gray), shape = RoundedCornerShape(6.dp)
            )
            .padding(15.dp)
    )
}