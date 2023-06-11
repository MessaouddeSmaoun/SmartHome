package com.example.maisoninteligente

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.SharedFlow


@Composable
fun TextAction(sharedFlow: SharedFlow<String>) {

    var txt by remember { mutableStateOf("0") }

LaunchedEffect(key1 = Unit) {
    sharedFlow.collect {
        when(it){
            "isOn" -> txt = "Alumer"
            "isOff" -> txt = "Eteint"
        }
    }
}

    Text(
        text = "Etat $txt",
        fontSize = 15.sp,
    )
}