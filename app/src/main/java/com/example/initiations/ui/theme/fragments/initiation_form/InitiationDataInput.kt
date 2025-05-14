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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.example.initiations.R
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.viewmodols.GoogleSheetsViewModel
import com.example.initiations.ui.theme.common_compose.CircularLoader
import com.example.initiations.ui.theme.common_compose.CustomAlertDialog
import com.example.initiations.ui.theme.common_compose.CustomElevatedButton
import com.example.initiations.ui.theme.common_compose.DynamicSelectTextField
import com.example.initiations.ui.theme.common_compose.OutlinedTextFieldCompose
import com.example.initiations.ui.theme.fragments.UploadCompleteScreen
import com.example.initiations.util.DateUtil
import com.example.initiations.util.UiState

@Composable
fun InitiationInputDataCompose(navigator: Navigator?){
    val viewmodel: GoogleSheetsViewModel = hiltViewModel()
    val formState = remember { mutableStateOf(InitiationFiled()) }

    val openDialogBox = remember {
        mutableStateOf(false)
    }
    val screenState by viewmodel.initiationState.collectAsState()

    val localContext = LocalContext.current



    Column(
        modifier = Modifier
            .padding(5.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        // collect all the input form
        InputCompose(formState)

        val validation = viewmodel.formInputValidation(formState.value)

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
                   val personId = DateUtil.generatePersonId(formState.value.personName)
                   formState.value = formState.value.copy(personId = personId)

                   openDialogBox.value = false
                   viewmodel.createRow(formState.value)

               },
               title = "Adding New Member",
               text = "These detail are important to be input correctly, Please confirm "
           )
        }

    }

    when (screenState){
        is UiState.Idle -> Unit
        is UiState.Loading -> CircularLoader("Saving the member...")
        is UiState.Success -> {
            Toast.makeText(localContext, "Initiation Successfully Added", Toast.LENGTH_SHORT).show()
            LaunchedEffect(Unit) {
                navigator?.push(UploadCompleteScreen())
            }
        }
        is UiState.Error -> {
            val error = (screenState as UiState.Error).message
            Toast.makeText(localContext, error ?: "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputCompose(formState: MutableState<InitiationFiled>) {

    FormInputField(
        value = formState.value.personName,
        onValueChange = { formState.value = formState.value.copy(personName = it) },
        placeholder = stringResource(id = R.string.person_name),
        icon = Icons.Default.Person,
        modifier = Modifier.fillMaxWidth(),
    )

    Row(
        modifier = Modifier.fillMaxWidth().padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FormInputField(
            value = formState.value.personAge,
            onValueChange = { formState.value = formState.value.copy(personAge = it) },
            placeholder = stringResource(id = R.string.age),
            icon = Icons.Filled.Numbers,
            keyboardType = KeyboardType.Number,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.weight(0.2f))

        DynamicSelectTextField(
            selectedValue = formState.value.gender,
            label = stringResource(id = R.string.gender),
            options = listOf(
                stringResource(id = R.string.male),
                stringResource(id = R.string.female)
            ),
            onValueChangedEvent = {
                formState.value = formState.value.copy(gender = it)
            },
            modifier = Modifier.weight(1f)
        )
    }

    FormInputField(
        value = formState.value.education,
        onValueChange = { formState.value = formState.value.copy(education = it) },
        placeholder = stringResource(id = R.string.education),
        icon = Icons.Filled.Edit,
        modifier = Modifier.fillMaxWidth()
    )

    FormInputField(
        value = formState.value.fullAddress,
        onValueChange = { formState.value = formState.value.copy(fullAddress = it) },
        placeholder = stringResource(id = R.string.full_address),
        icon = Icons.Default.Edit,
        modifier = Modifier.fillMaxWidth()
    )

    FormInputField(
        value = formState.value.contact,
        onValueChange = { formState.value = formState.value.copy(contact = it) },
        placeholder = stringResource(id = R.string.contact),
        icon = Icons.Default.Edit,
        keyboardType = KeyboardType.Number,
        modifier = Modifier.fillMaxWidth()
    )

    FormInputField(
        value = formState.value.masterName,
        onValueChange = { formState.value = formState.value.copy(masterName = it) },
        placeholder = stringResource(id = R.string.master_name),
        icon = Icons.Default.Person,
        modifier = Modifier.fillMaxWidth()
    )

    FormInputField(
        value = formState.value.introducerName,
        onValueChange = { formState.value = formState.value.copy(introducerName = it) },
        placeholder = stringResource(id = R.string.introducer_name),
        icon = Icons.Default.Person,
        modifier = Modifier.fillMaxWidth()
    )

    FormInputField(
        value = formState.value.guarantorName,
        onValueChange = { formState.value = formState.value.copy(guarantorName = it) },
        placeholder = stringResource(id = R.string.guarantor_name),
        icon = Icons.Default.Person,
        modifier = Modifier.fillMaxWidth()
    )

    FormInputField(
        value = formState.value.templeName,
        onValueChange = { formState.value = formState.value.copy(templeName = it) },
        placeholder = stringResource(id = R.string.temple_name),
        icon = Icons.Default.Edit,
        modifier = Modifier.fillMaxWidth()
    )

    FormInputField(
        value = formState.value.meritFee,
        onValueChange = { formState.value = formState.value.copy(meritFee = it) },
        placeholder = stringResource(id = R.string.merits_fee),
        icon = Icons.Default.Edit,
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done,
        modifier = Modifier.fillMaxWidth()
    )

    // Date Picker
    val showDialogBox = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val datePickerToFormat = DateUtil.convertMillisecondToDate(datePickerState.selectedDateMillis)

    formState.value = formState.value.copy(initiationDate = datePickerToFormat)

    Text(
        text = datePickerToFormat,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { showDialogBox.value = true }
            .background(Color.Transparent, RoundedCornerShape(6.dp))
            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(6.dp))
            .padding(15.dp)
    )

    if (showDialogBox.value) {
        DatePickerDialog(
            onDismissRequest = { showDialogBox.value = false },
            confirmButton = {
                Button(onClick = { showDialogBox.value = false }) { Text("OK") }
            },
            dismissButton = {
                Button(onClick = { showDialogBox.value = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = true,
                colors = DatePickerDefaults.colors()
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Checkbox(
            checked = formState.value.is2DaysDharmaClassAttend,
            onCheckedChange = {
                formState.value = formState.value.copy(is2DaysDharmaClassAttend = it)
            }
        )
        Text(text = "2 Day Dharma meeting Attended")
    }
}


@Composable
fun FormInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier
) {
    OutlinedTextFieldCompose(
        text = value,
        onTextChanged = onValueChange,
        placeHolder = placeholder,
        leadingIcon = icon,
        keyBoardOption = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        modifier = modifier
    )
}
