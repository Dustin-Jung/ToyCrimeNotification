package com.android.aop.part2.toycrimenotification.ui.home

import com.android.aop.part2.toycrimenotification.base.ViewState

sealed class HomeViewState : ViewState {
    object PermissionGrant : HomeViewState()
    data class Error(val errorMessage: String) : HomeViewState()
}
