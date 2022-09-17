package com.android.aop.part2.toycrimenotification.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.android.aop.part2.toycrimenotification.R
import com.android.aop.part2.toycrimenotification.base.BaseActivity
import com.android.aop.part2.toycrimenotification.databinding.ActivitySplashBinding
import com.android.aop.part2.toycrimenotification.ext.showToast
import com.android.aop.part2.toycrimenotification.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.system.exitProcess

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash){


    private var isRoute = false
    private val splashViewModel by viewModels<SplashViewModel>()

    private val lottieAnimationListener = object : Animator.AnimatorListener {

        override fun onAnimationStart(p0: Animator) {

        }

        override fun onAnimationEnd(p0: Animator) {

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

        override fun onAnimationCancel(p0: Animator) {

        }

        override fun onAnimationRepeat(p0: Animator) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        initViewModel()
        getHashKey()
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

    @SuppressLint("PackageManagerGetSignatures")
    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }

}