package com.shroomlife.shliste.state

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AppStoreViewModel : ViewModel() {

    var bottomNavType = mutableStateOf(BottomNavType.NONE)
        private set

    fun setBottomNav(type: BottomNavType) {
        bottomNavType.value = type
    }
}
