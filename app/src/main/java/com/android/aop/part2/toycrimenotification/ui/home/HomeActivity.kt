package com.android.aop.part2.toycrimenotification.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import com.android.aop.part2.toycrimenotification.BuildConfig
import com.android.aop.part2.toycrimenotification.R
import com.android.aop.part2.toycrimenotification.base.BaseActivity
import com.android.aop.part2.toycrimenotification.base.ViewState
import com.android.aop.part2.toycrimenotification.databinding.ActivityHomeBinding
import com.android.aop.part2.toycrimenotification.ext.showToast
import com.android.aop.part2.toycrimenotification.ui.map.MapFragment.Companion.REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val homeViewModel by viewModels<HomeViewModel>()

    private var backWait: Long = INIT_TIME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
    }

    private fun initViewModel() {
        homeViewModel.viewStateLiveData.observe(this) { viewState: ViewState? ->
            (viewState as? HomeViewState)?.let { onChangedHomeViewState(viewState) }
        }
    }

    private fun onChangedHomeViewState(viewState: HomeViewState) {
        when (viewState) {

            is HomeViewState.Error -> {
                showToast(message = viewState.errorMessage)
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE){

            when{

                grantResults.isEmpty() -> {
                    showToast(message = "권한이 없습니다.")
                }

                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    showToast(message = "권한이 허용되었습니다.")
                    homeViewModel.permissionGrant()
                }

                else -> {
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts(
                        "package",
                        BuildConfig.APPLICATION_ID,
                        null
                    )
                    intent.data = uri
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backWait >= LIMIT_TIME) {
            backWait = System.currentTimeMillis()
            showToast(message = "뒤로가기 버튼을 한번 더 누르면 종료됩니다.")
        } else {
            super.onBackPressed()
        }
    }

    companion object {

        private const val INIT_TIME = 0L
        private const val LIMIT_TIME = 3000

    }
}