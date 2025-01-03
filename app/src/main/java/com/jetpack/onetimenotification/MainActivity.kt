package com.jetpack.onetimenotification

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.WorkManager
import com.jetpack.onetimenotification.ui.theme.OneTimeNotificationWorkManagerTheme

class MainActivity : ComponentActivity() {

    private lateinit var workManager: WorkManager

    val requestAllPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialization work manager
        workManager = WorkManager.getInstance(applicationContext)

        // Notification permission included
        if (Build.VERSION.SDK_INT >= 33) {
           requestAllPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            OneTimeNotificationWorkManagerTheme {

                val mainViewModel : MainViewModel by viewModels {
                    viewModelFactory { initializer { MainViewModel() } }
                }

                val message = mainViewModel.message.collectAsState().value

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = message.messages ?: "",
                            label = {
                                Text("Input description")
                            },
                            onValueChange = { value ->
                                mainViewModel.changeMessage(value)
                            }
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                               workManager.enqueue(mainViewModel.startOneTimeNotification())
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

