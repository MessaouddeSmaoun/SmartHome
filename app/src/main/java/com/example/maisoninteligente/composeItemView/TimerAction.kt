package com.example.maisoninteligente.composeItemView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maisoninteligente.R
import com.example.maisoninteligente.ViewModelAction
import kotlinx.coroutines.flow.SharedFlow


@Composable
fun TimerAction(
    refButton: Int,
    viewModel: ViewModelAction
) {
    var timerNumber by remember { mutableStateOf(10) }
    var isEnableStat by remember { mutableStateOf(true) }
    var isEnableMode by remember { mutableStateOf(true) }

    lateinit var sharedFlowStat: SharedFlow<String>
    lateinit var sharedFlowMode: SharedFlow<String>

    val isOn = stringResource(id = R.string.isOn)
    val isOff = stringResource(id = R.string.isOff)
    val click = stringResource(id = R.string.click)
    val chrono = stringResource(id = R.string.chrono)

    when (refButton) {
        1 -> {
            sharedFlowStat = viewModel.statButton1
            sharedFlowMode = viewModel.modeFlowButton1
        }

        2 -> {
            sharedFlowStat = viewModel.statButton2
            sharedFlowMode = viewModel.modeFlowButton2
        }
    }


    LaunchedEffect(key1 = Unit) {
        sharedFlowStat.collect {
            when (it) {
                isOn -> isEnableStat = false
                isOff -> isEnableStat = true
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        sharedFlowMode.collect {
            when (it) {
                click -> isEnableMode = false
                chrono -> isEnableMode = true
            }
        }
    }
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                if (timerNumber > 0) timerNumber--
                viewModel.setTimerButton(ref = refButton, updateTimer = timerNumber)
            },
            enabled = isEnableStat,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
        ) {
            Text(
                text = "-",
                fontSize = 18.sp
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(vertical = 10.dp, horizontal = 10.dp),
            text = timerNumber.toString(),
            textAlign = TextAlign.Center

        )


        Button(
            onClick = {
                if (20 > timerNumber) timerNumber++
                viewModel.setTimerButton(ref = refButton, updateTimer = timerNumber)
            },
            enabled = isEnableStat,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue))
        ) {
            Text(
                text = "+",
                fontSize = 18.sp
            )
        }

    }
}