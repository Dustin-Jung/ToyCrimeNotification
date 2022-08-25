package com.android.aop.part2.toycrimenotification.ui.splash

import com.android.aop.part2.toycrimenotification.base.ViewState

sealed class SplashViewState : ViewState {
    object RouteHome: SplashViewState()
    data class Error(val message: String): SplashViewState()
}
