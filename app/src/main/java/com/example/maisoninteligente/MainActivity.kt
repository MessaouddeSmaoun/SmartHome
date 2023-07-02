package com.example.maisoninteligente

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.maisoninteligente.composeItemView.SpinnerDown
import com.example.maisoninteligente.composeItemView.TimerAction
import com.example.maisoninteligente.ui.theme.MaisonInteligenteTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ViewModelAction>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
  /*          LaunchedEffect(key1 = Unit) {
                while (true){
                    viewModel.setStatConnexion()
                    delay(5000)
                }
            }
        */
            MaisonInteligenteTheme {

                var isConnected by remember { mutableStateOf(true) }
                var url by remember { mutableStateOf("www.google.com") }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = ""
                        )


                        Row(horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            Switch(
                                checked = isConnected,
                                onCheckedChange = { isConnected = it }
                            )



                            Spacer(modifier = Modifier.width(10.dp))
                            LedAction(viewModel.statConnexion)
                        }

                        Column( modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {

                            if(!isConnected) {
                                Button(
                                    onClick = { url = "192.168.1.23" }
                                ) {
                                    Text(text = "refresh",)
                                }
                                pageNotConnected(url)
                            } else  pageConnected(viewModel)
                        }

                        Text(
                            text = "Appaznay 2023!",
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun pageConnected(viewModel: ViewModelAction) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextAction(viewModel.statButton1)
            Spacer(modifier = Modifier.height(40.dp))
            SpinnerDown(1, viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            TimerAction(1, viewModel)
            Spacer(modifier = Modifier.height(40.dp))
            ButtonAction(1, viewModel)

        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextAction(viewModel.statButton2)
            Spacer(modifier = Modifier.height(40.dp))
            SpinnerDown(2, viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            TimerAction(2, viewModel)
            Spacer(modifier = Modifier.height(40.dp))
            ButtonAction(2, viewModel)
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun pageNotConnected(url : String) {

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }

    }, update = {
        it.loadUrl(url)
    })



}

