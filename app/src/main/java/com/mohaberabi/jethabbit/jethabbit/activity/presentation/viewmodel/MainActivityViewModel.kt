package com.mohaberabi.jethabbit.jethabbit.activity.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jethabbit.features.auth.domain.repository.UserRepository
import com.mohaberabi.jethabbit.jethabbit.activity.domain.usecase.CheckAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
) : ViewModel() {


    var hasLoggedIn by mutableStateOf(false)
        private set

    var loadedData by mutableStateOf(false)
        private set

    init {
        loadData()
    }


    private fun loadData() {

        try {
            viewModelScope.launch {
                hasLoggedIn = checkAuthStateUseCase()
                loadedData = true
            }
        } catch (e: Exception) {
            loadedData = true
            e.printStackTrace()
        }
    }

}

