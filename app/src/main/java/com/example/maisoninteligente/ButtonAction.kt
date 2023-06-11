package com.example.maisoninteligente

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ButtonAction(
    nameButton: String,
    refButton: String,
    viewModel: ViewModelAction,
    sharedFlowStat: SharedFlow<String>,
    sharedFlowColor: SharedFlow<Int>
) {
    var txt by remember { mutableStateOf("") }
    var colorBack by remember { mutableStateOf(R.color.green) }

    LaunchedEffect(key1 = Unit) {
        sharedFlowStat.collect {
            when(it){
                "isOn"  -> txt = "setOff"
                "isOff" -> txt = "setOn"
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        sharedFlowColor.collect {
            colorBack = it
        }
    }

    Button(
        onClick = {
            viewModel.setStatButton(refButton, txt)
            Log.i("tag","click")
        },
        colors = ButtonDefaults.buttonColors(colorResource(id = colorBack)),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        Text(text = nameButton)
    }
}