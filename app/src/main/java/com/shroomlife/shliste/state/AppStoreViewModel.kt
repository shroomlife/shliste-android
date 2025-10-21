package com.shroomlife.shliste.state

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppStoreViewModel : ViewModel() {

    var bottomNavType = mutableStateOf(BottomNavType.NONE)
        private set

    var scrollState by mutableStateOf<ScrollState?>(null)
        private set

    fun setBottomNav(type: BottomNavType) {
        bottomNavType.value = type
    }

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
