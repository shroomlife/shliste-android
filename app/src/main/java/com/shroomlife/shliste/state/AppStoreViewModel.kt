package com.shroomlife.shliste.state

import android.app.Application
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shroomlife.shliste.utils.AppUtils
import kotlinx.coroutines.launch

class AppStoreViewModel(application: Application) : AndroidViewModel(application) {

    var scrollState by mutableStateOf<ScrollState?>(null)
        private set

    var biometricAvailable by mutableStateOf(false)
        private set

    init {
        checkBiometricAvailability()
    }

    private fun checkBiometricAvailability() {
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            biometricAvailable = AppUtils.isBiometricAvailable(context)
        }
    }

    fun isBiometricAvailable(): Boolean = biometricAvailable

    fun updateScrollState(state: ScrollState) {
        scrollState = state
    }

    fun scrollToTop() {
        viewModelScope.launch {
            scrollState?.scrollTo(0)
        }
    }

    fun scrollToBottom() {
        viewModelScope.launch {
            scrollState?.scrollTo(scrollState?.maxValue ?: 0)
        }
    }

    fun smoothScrollToBottom() {
        viewModelScope.launch {
            scrollState?.animateScrollTo(scrollState?.maxValue ?: 0)
        }
    }

    fun smoothScrollToTop() {
        viewModelScope.launch {
            scrollState?.animateScrollTo(0)
        }
    }
}
