package com.android.aop.part2.toycrimenotification.ui.map

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.aop.part2.toycrimenotification.base.BaseViewModel
import com.android.aop.part2.toycrimenotification.data.repo.CriminalRepository
import com.android.aop.part2.toycrimenotification.data.repo.FirebaseRepository
import com.android.aop.part2.toycrimenotification.ext.defaultScope
import com.android.aop.part2.toycrimenotification.ext.ioScope
import com.android.aop.part2.toycrimenotification.ext.uiScope
import com.android.aop.part2.toycrimenotification.util.DistanceManager
import com.android.aop.part2.toycrimenotification.util.GpsTracker
import com.android.aop.part2.toycrimenotification.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import net.daum.mf.map.api.MapPoint
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository,
    private val criminalRepository: CriminalRepository
): BaseViewModel(app){

    private val gpsTracker = GpsTracker(app)

    val currentCenterMapPoint = MutableLiveData<MapPoint>()

    val currentZoomLevel = MutableLiveData<Int>()

    var settingRoundCriminal: Int = 5000

    private lateinit var renewJob: Job


    fun showUserPopupMenu() {
        viewStateChanged(MapViewState.ShowUserPopupMenu)
    }


    fun withdraw() {
        ioScope {
            firebaseRepository.delete()?.run {
                addOnSuccessListener { viewStateChanged(MapViewState.WithdrawUser) }
                addOnFailureListener { viewStateChanged(MapViewState.Error("회원탈퇴를 실패하였습니다.")) }
            }
        }
    }

    fun logout() {
        ioScope {
            if (firebaseRepository.logout()) {
                viewStateChanged(MapViewState.LogoutUser)
            } else {
                viewStateChanged(MapViewState.Error("로그아웃이 실패하였습니다."))
            }
        }
    }

    fun callPolice() {
        viewStateChanged(MapViewState.CallPolice)
    }

    fun aroundCriminalList() {
        viewStateChanged(MapViewState.RouteAroundCriminalList)
    }



    fun setCurrentLocation() {

        ioScope {

            if (::renewJob.isInitialized && renewJob.isActive) {
                renewJob.cancel()
            }

            viewStateChanged(MapViewState.ShowProgress)

            when (val result = gpsTracker.getLocation()) {

                is Result.Success -> {
                    result.data.addOnCompleteListener { task ->
                        val location = task.result

                        val resultMapPoint =
                            MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude)
                        viewStateChanged(MapViewState.SetCurrentLocation(resultMapPoint))
                        viewStateChanged(MapViewState.HideProgress)
                        viewStateChanged(MapViewState.SetZoomLevel(4))
                    }

                    ioScope {
                        renewCurrentLocation()
                    }
                }
                is Result.Error -> {
                    viewStateChanged(MapViewState.Error(result.exception.message.toString()))
                    viewStateChanged(MapViewState.HideProgress)
                }
            }
        }
    }

    private suspend fun renewCurrentLocation() {

        renewJob = ioScope {
            while (true) {

                delay(RENEW_CURRENT_LOCATION_INTERVAL)

                when (val result = gpsTracker.getLocation()) {
                    is Result.Success -> {
                        result.data.addOnCompleteListener { task ->
                            val location = task.result
                            val resultMapPoint =
                                MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude)

                            ioScope {

                                when (val criminalResult = criminalRepository.getLocalCriminals()) {
                                    is Result.Success -> {

                                        val toAroundList = criminalResult.data.filter {
                                            DistanceManager.getDistance(
                                                lat1 = location.latitude,
                                                lon1 = location.longitude,
                                                lat2 = it.latitude,
                                                lon2 = it.longitude
                                            ) <= settingRoundCriminal
                                        }

                                        if (toAroundList.isNotEmpty()) {
                                            viewStateChanged(
                                                MapViewState.AroundCriminals(
                                                    toAroundList
                                                )
                                            )
                                        }

                                        viewStateChanged(
                                            MapViewState.RenewCurrentLocation(
                                                resultMapPoint
                                            )
                                        )
                                    }

                                    is Result.Error -> {

                                        viewStateChanged(MapViewState.Error("범죄자 데이터 호출에 실패하였습니다."))
                                    }
                                }
                            }
                        }
                    }
                    is Result.Error -> {

                        viewStateChanged(MapViewState.Error(result.exception.message.toString()))
                    }
                }
            }
        }

        renewJob.join()
    }

    fun showCriminals() {
        viewStateChanged(MapViewState.ShowProgress)
        ioScope {
            when (val result = criminalRepository.getLocalCriminals()) {

                is Result.Success -> {
                    val toMapPOIItem = result.data.map { it.toCriminalItem().toMapPOIItem() }
                    viewStateChanged(MapViewState.GetCriminalItems(toMapPOIItem.toTypedArray()))
                }

                is Result.Error -> {
                    viewStateChanged(MapViewState.Error("범죄자 데이터 호출에 실패하였습니다."))
                }


            }
        }
    }

    fun getSelectPOIItemInfo(itemName: String) {
        ioScope {

            when (val criminalResult = criminalRepository.getCriminalEntity(itemName)) {
                is Result.Success -> {
                    ioScope {

                        when (val locationResult = gpsTracker.getLocation()) {
                            is Result.Success -> {

                                locationResult.data.addOnCompleteListener { task ->
                                    val location = task.result
                                    defaultScope {
                                        val distance = DistanceManager.getDistance(
                                            lat1 = location.latitude,
                                            lon1 = location.longitude,
                                            lat2 = criminalResult.data.latitude,
                                            lon2 = criminalResult.data.longitude
                                        )
                                        uiScope {
                                            viewStateChanged(
                                                MapViewState.GetSelectPOIItem(
                                                    criminalResult.data.toCriminalItem(),
                                                    DistanceManager.toStringDistance(distance)
                                                )
                                            )
                                        }
                                    }
                                }
                            }

                            is Result.Error -> {
                                viewStateChanged(MapViewState.Error(locationResult.exception.message.toString()))
                            }
                        }
                    }
                }

                is Result.Error -> {
                    viewStateChanged(MapViewState.Error(criminalResult.exception.message.toString()))
                }
            }
        }
    }

    companion object {

        private const val RENEW_CURRENT_LOCATION_INTERVAL = 2000L

    }

}