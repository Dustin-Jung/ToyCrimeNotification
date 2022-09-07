package com.android.aop.part2.toycrimenotification.ui.criminallist

import android.app.Application
import androidx.databinding.ObservableField
import com.android.aop.part2.toycrimenotification.base.BaseViewModel
import com.android.aop.part2.toycrimenotification.data.model.DistanceCriminal
import com.android.aop.part2.toycrimenotification.data.repo.CriminalRepository
import com.android.aop.part2.toycrimenotification.ext.ioScope
import com.android.aop.part2.toycrimenotification.util.DistanceManager
import com.android.aop.part2.toycrimenotification.util.GpsTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import com.android.aop.part2.toycrimenotification.util.Result

@HiltViewModel
class CriminalListViewModel @Inject constructor(
    app: Application,
    private val criminalRepository: CriminalRepository
) : BaseViewModel(app) {

    val rangeObservableField = ObservableField<Int>()

    private val gpsTracker = GpsTracker(app)

    fun getCriminalList() {
        ioScope {

            while (true) {

                viewStateChanged(CriminalListViewState.ShowProgress)
                delay(DELAY_PROGRESS)

                when (val result = gpsTracker.getLocation()) {
                    is Result.Success -> {
                        result.data.addOnCompleteListener { task ->
                            val location = task.result
                            ioScope {

                                when (val criminalResult = criminalRepository.getLocalCriminals()) {
                                    is Result.Success -> {

                                        val toAroundList = criminalResult.data.filter {
                                            DistanceManager.getDistance(
                                                lat1 = location.latitude,
                                                lon1 = location.longitude,
                                                lat2 = it.latitude,
                                                lon2 = it.longitude
                                            ) <= rangeObservableField.get()!!
                                        }.map {
                                            DistanceCriminal(
                                                name = it.name,
                                                address = it.address,
                                                distance = DistanceManager.getDistance(
                                                    lat1 = location.latitude,
                                                    lon1 = location.longitude,
                                                    lat2 = it.latitude,
                                                    lon2 = it.longitude
                                                )
                                            )
                                        }


                                        if (toAroundList.isNotEmpty()) {

                                            viewStateChanged(
                                                CriminalListViewState.RenewCriminalList(
                                                    toAroundList
                                                )
                                            )
                                        } else {

                                            viewStateChanged(CriminalListViewState.EmptyCriminalList)
                                        }
                                        viewStateChanged(CriminalListViewState.HideProgress)
                                    }


                                    is Result.Error -> {
                                        viewStateChanged(CriminalListViewState.HideProgress)
                                    }
                                }
                            }
                        }
                    }

                    is Result.Error -> {
                        viewStateChanged(CriminalListViewState.Error(result.exception.message.toString()))
                        viewStateChanged(CriminalListViewState.HideProgress)
                    }
                }

                delay(RENEW_INTERVAL)
            }
        }
    }

    companion object {
        private const val DELAY_PROGRESS = 1000L
        private const val RENEW_INTERVAL = 5000L
    }
}