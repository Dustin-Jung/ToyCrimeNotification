package com.android.aop.part2.toycrimenotification.ui.register

import com.android.aop.part2.toycrimenotification.base.ViewState

sealed class RegisterViewState : ViewState {
    object RouteHome : RegisterViewState()
    data class Error(val message: String) : RegisterViewState()
    data class EnableInput(val isEnable: Boolean) : RegisterViewState()
    object ShowProgress : RegisterViewState()
    object HideProgress : RegisterViewState()
}
