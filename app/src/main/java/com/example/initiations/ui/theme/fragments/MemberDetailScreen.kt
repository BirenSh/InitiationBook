package com.example.initiations.ui.theme.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.initiations.R
import com.example.initiations.di.entities.InitiationFiled

//@Preview(showSystemUi = true)
@Composable
fun MemberDetailScreen(passedArg:InitiationFiled) {
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .background(colorResource(id = R.color.orange_light))
                .padding(5.dp),
        ) {
            Column {
                Text(
                    text = passedArg.personName, // TODO: change to passedArg.PersonName
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(color = Color.Black, blurRadius = 15f, offset = Offset(10.0f,12.0f)),
                        fontStyle = FontStyle.Italic),
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = passedArg.initiationDate, // TODO: change to passedArg.templename
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(3.dp))
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.LightGray)
                .padding(5.dp)
        ) {
            DetailWithValue(stringResource(id = R.string.master_name),passedArg.masterName)
            DetailWithValue(stringResource(id = R.string.introducer_name),passedArg.introducerName)
            DetailWithValue(stringResource(id = R.string.guarantor_name),passedArg.guarantorName)
            DetailWithValue(stringResource(id = R.string.education),passedArg.education)
            DetailWithValue(stringResource(id = R.string.full_address),passedArg.fullAddress)
            DetailWithValue(stringResource(id = R.string.temple_name),passedArg.templeName)
            DetailWithValue(stringResource(id = R.string.age),passedArg.personAge.toString())
            DetailWithValue(stringResource(id = R.string.gender),passedArg.gender)
            DetailWithValue(stringResource(id = R.string.merits_fee),passedArg.meritFee)
        }
    }

}

@Composable
fun DetailWithValue(label:String,labelValue:String) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(3.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(5.dp)
        ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            //Text for label
            Text(text = label, color = Color.Gray, fontSize = 16.sp)
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            //Text for value
            Text(text = labelValue, fontSize = 18.sp)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewDetail(){
    MemberDetailScreen(InitiationFiled(id = 1) )
}