package com.android.aop.part2.toycrimenotification.ui.home

import android.app.Application
import com.android.aop.part2.toycrimenotification.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(app: Application): BaseViewModel(app){

    fun permissionGrant(){
        viewStateChanged(HomeViewState.PermissionGrant)
    }
}