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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ButtonAction(
    refButton: Int,
    viewModel: ViewModelAction,
) {

    val context = LocalContext.current
    var stat by remember { mutableStateOf("") }
    var timer by remember { mutableStateOf(10) }
    var mode by remember { mutableStateOf("Click") }
    var colorBack by remember { mutableStateOf(R.color.green) }

    val isOn = stringResource(id = R.string.isOn)
    val isOff = stringResource(id = R.string.isOff)
    val seton = stringResource(id = R.string.seton)
    val setoff = stringResource(id = R.string.setoff)

    lateinit var sharedFlowColor : SharedFlow<Int>
    lateinit var sharedFlowStat : SharedFlow<String>
    lateinit var sharedFlowMode : SharedFlow<String>
    lateinit var sharedFlowTimer : SharedFlow<Int>
    lateinit var sharedFlowEnable : SharedFlow<Boolean>



    when (refButton) {
       1 -> {
           sharedFlowStat = viewModel.statButton1
           sharedFlowColor = viewModel.colorButton1
           sharedFlowMode = viewModel.modeFlowButton1
           sharedFlowTimer = viewModel.timerFlowButton1
       }
       2 -> {
           sharedFlowStat = viewModel.statButton2
           sharedFlowColor = viewModel.colorButton2
           sharedFlowMode = viewModel.modeFlowButton2
           sharedFlowTimer = viewModel.timerFlowButton2
       }
    }

    LaunchedEffect(key1 = Unit) {
        sharedFlowMode.collect {
            mode = it
        }
    }
    LaunchedEffect(key1 = Unit) {
        sharedFlowTimer.collect {
            timer = it
        }
    }
    LaunchedEffect(key1 = Unit) {
        sharedFlowStat.collect {
            when(it){
                isOn  -> stat = setoff
                isOff -> stat = seton
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
            viewModel.setStatButton(context, refButton,stat = stat, mode = mode, timer = timer)
            Log.i("tag","click")
        },
        colors = ButtonDefaults.buttonColors(colorResource(id = colorBack)),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        Text(text = "Button $refButton")
    }
}