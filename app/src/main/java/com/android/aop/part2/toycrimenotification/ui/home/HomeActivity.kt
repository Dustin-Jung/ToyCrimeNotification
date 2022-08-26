package com.android.aop.part2.toycrimenotification.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import com.android.aop.part2.toycrimenotification.R
import com.android.aop.part2.toycrimenotification.base.BaseActivity
import com.android.aop.part2.toycrimenotification.base.ViewState
import com.android.aop.part2.toycrimenotification.databinding.ActivityHomeBinding
import com.android.aop.part2.toycrimenotification.ext.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val homeViewModel by viewModels<HomeViewModel>()

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
}