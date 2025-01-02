package com.jetpack.onetimenotification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jetpack.onetimenotification.ui.theme.OneTimeNotificationWorkManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OneTimeNotificationWorkManagerTheme {

                var desc by remember {
                    mutableStateOf("")
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = desc,
                            label = {
                                Text("Input description")
                            },
                            onValueChange = { value ->
                                desc = value
                            }
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {

                            }
                        ) {
                            Text("Submit")
                        }
                    }
                }
            }
        }
    }
}
