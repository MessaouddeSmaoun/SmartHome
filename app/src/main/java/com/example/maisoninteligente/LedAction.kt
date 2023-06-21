package com.example.maisoninteligente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun LedAction(statConnexion: SharedFlow<Int>) {

    var colorBack by remember { mutableStateOf(R.color.green) }

    LaunchedEffect(key1 = Unit) {
        statConnexion.collect {
            colorBack = it
        }
    }

    Row (
        modifier = Modifier
            .padding(20.dp),
        horizontalArrangement = Arrangement.End
    ){

        Text(
            text = "State of Connexion",
            fontSize = 15.sp,
        )
        Spacer(modifier = Modifier.width(15.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_led),
            contentDescription = "",
            tint = colorResource(
                id = colorBack
            )
        )
    }



}