package com.android.aop.part2.toycrimenotification.ui.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.aop.part2.toycrimenotification.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(app: Application) : BaseViewModel(app) {

    val inputEmailLiveData = MutableLiveData<String>()
    val inputPasswordLiveData = MutableLiveData<String>()

    fun login(){

    }

    fun register(){

    }

}

