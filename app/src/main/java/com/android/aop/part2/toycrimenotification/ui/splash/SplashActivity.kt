package com.android.aop.part2.toycrimenotification.ui.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.android.aop.part2.toycrimenotification.R
import com.android.aop.part2.toycrimenotification.base.BaseActivity
import com.android.aop.part2.toycrimenotification.databinding.ActivitySplashBinding
import com.android.aop.part2.toycrimenotification.ext.showToast
import com.android.aop.part2.toycrimenotification.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash){


    private var isRoute = false
    private val splashViewModel by viewModels<SplashViewModel>()

    private val lottieAnimationListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            if (isRoute) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                })

            } else {
                with(binding) {
                    tvLoading.isVisible = true
                    tvLoading.startAnimation(
                        AnimationUtils.loadAnimation(
                            applicationContext,
                            R.anim.anim_brick
                        )
                    )
                    lottieView.playAnimation()
                }

            }
        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationRepeat(animation: Animator?) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        initViewModel()
    }

    private fun initUi() {
        binding.lottieView.addAnimatorListener(lottieAnimationListener)
    }

    private fun initViewModel() {
        splashViewModel.viewStateLiveData.observe(this) { viewstate ->
            (viewstate as? SplashViewState)?.let {
                onChangedSplashViewState(
                    viewstate
                )
            }

        }
    }

    private fun onChangedSplashViewState(viewState: SplashViewState) {

        when (viewState) {

            is SplashViewState.RouteHome -> {
                isRoute = true
            }

            is SplashViewState.Error -> {
                showToast(message = viewState.message)
                exitProcess(0)
            }
        }


    }

}