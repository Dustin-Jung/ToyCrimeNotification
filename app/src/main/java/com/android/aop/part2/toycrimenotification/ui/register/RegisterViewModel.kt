package com.android.aop.part2.toycrimenotification.ui.register

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.aop.part2.toycrimenotification.base.BaseViewModel
import com.android.aop.part2.toycrimenotification.data.repo.FirebaseRepository
import com.android.aop.part2.toycrimenotification.ext.ioScope
import com.android.aop.part2.toycrimenotification.ext.register
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {

    val inputEmailLiveData = MutableLiveData<String>()
    val inputPasswordLiveData = MutableLiveData<String>()
    val inputPasswordOkLiveData = MutableLiveData<String>()

    fun register() {
        ioScope {
            viewStateChanged(RegisterViewState.ShowProgress)
            viewStateChanged(RegisterViewState.EnableInput(false))

            val checkEmail = async { checkEmail() }
            val checkPassword = async { checkPassword() }
            val checkPasswordOk = async { checkPasswordOk() }

            checkUser(checkEmail.await(), checkPassword.await(), checkPasswordOk.await())?.let {

                firebaseRepository.register(it.email, it.password) { isRegister ->
                    if (isRegister) {
                        viewStateChanged(RegisterViewState.RouteHome)
                    } else {
                        viewStateChanged(RegisterViewState.Error("회원가입을 실패하였습니다."))
                    }
                }
            }

            viewStateChanged(RegisterViewState.HideProgress)
            viewStateChanged(RegisterViewState.EnableInput(true))
        }

    }

    private fun checkUser(
        checkEmail: Boolean,
        checkPassword: Boolean,
        checkPasswordOk: Boolean,
    ): Person? {
        return if (checkEmail && checkPassword && checkPasswordOk) {
            Person(inputEmailLiveData.value!!, inputPasswordLiveData.value!!)
        } else {
            null
        }
    }

    private fun checkEmail(): Boolean {
        return when {
            inputEmailLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(RegisterViewState.Error("이메일을 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    private fun checkPassword(): Boolean {
        return when {
            inputPasswordLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(RegisterViewState.Error("비밀번호를 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    private fun checkPasswordOk(): Boolean {
        return when {
            inputPasswordLiveData.value != inputPasswordOkLiveData.value -> {
                viewStateChanged(RegisterViewState.Error("비밀번호 재입력을 올바르게 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    data class Person(
        val email: String,
        val password: String
    )
}