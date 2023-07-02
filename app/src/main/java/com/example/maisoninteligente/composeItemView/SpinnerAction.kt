package com.example.maisoninteligente.composeItemView


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.maisoninteligente.R
import com.example.maisoninteligente.ViewModelAction
import kotlinx.coroutines.flow.SharedFlow


@Composable
    fun SpinnerDown(
        refButton: Int,
        viewModel: ViewModelAction) {
        var expanded by remember { mutableStateOf(false) }
        val items = listOf(stringResource(id = R.string.chrono), stringResource(id = R.string.click))
        var selectedItems by remember { mutableStateOf(items[0]) }
        lateinit var mode : String


        lateinit var sharedFlowMode : SharedFlow<String>

        when (refButton) {
            1 -> {
                sharedFlowMode = viewModel.modeFlowButton1
            }
            2 -> {
                sharedFlowMode = viewModel.modeFlowButton2
            }
        }

        LaunchedEffect(key1 = Unit) {
            sharedFlowMode.collect {
                selectedItems = it
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()) {
            Text(selectedItems,
                textAlign = TextAlign.Center,
                modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clickable(onClick = { expanded = true })
                .background(
                    color = colorResource(id = R.color.blueGrey)
                ))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.blueGrey)
                    )
            ) {
                items.forEachIndexed { index, _ ->
                    DropdownMenuItem(
                        text = {
                            Text(text = items[index])
                        },
                        onClick = {
                            selectedItems = items[index]
                            viewModel.setModeButton(refButton,items[index])
                            expanded = false
                            Log.d("tag","selectedItems $selectedItems")
                        },
                    )
                }
            }
        }
    }