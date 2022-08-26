package com.android.aop.part2.toycrimenotification.ui.map

import android.app.Application
import com.android.aop.part2.toycrimenotification.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(app: Application) : BaseViewModel(app){
}