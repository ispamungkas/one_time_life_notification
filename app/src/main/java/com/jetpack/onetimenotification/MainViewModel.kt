package com.jetpack.onetimenotification

import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel: ViewModel() {

    // Getter setter for state
    private val _messages = MutableStateFlow(MainState());
    var message : StateFlow<MainState> = _messages.asStateFlow()

    // Bussiness logic

    fun changeMessage(message: String) {
        _messages.update { mainState ->
            mainState.copy(
                messages = message
            )
        }
    }

    fun startOneTimeNotification(): OneTimeWorkRequest {
        val data = Data.Builder()
            .putString(MyWorkers.DESC, message.value.messages)
            .build()
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(networkType = NetworkType.CONNECTED)
            .build()
        return OneTimeWorkRequest.Builder(MyWorkers::class)
            .setInputData(inputData = data)
            .setConstraints(constraint)
            .build()

    }
}