package com.hardus.trueagencyapp.onboarding.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardus.trueagencyapp.nested_navigation.AUTH_GRAPH_ROUTE
import com.hardus.trueagencyapp.nested_navigation.ONBOARDING_GRAPH_ROUTE
import com.hardus.trueagencyapp.nested_navigation.Screen
import com.hardus.trueagencyapp.onboarding.data.DataStoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {
    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading
    private val _startDestination: MutableState<String> =
        mutableStateOf(ONBOARDING_GRAPH_ROUTE)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            repository.readOnBoardingState().collect { completed ->
                if (completed) {
                    _startDestination.value = AUTH_GRAPH_ROUTE
                } else {
                    _startDestination.value = ONBOARDING_GRAPH_ROUTE
                }
            }
            _isLoading.value = false
        }
    }
}