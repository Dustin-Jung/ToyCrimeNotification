package com.android.aop.part2.toycrimenotification.ui.map

import com.android.aop.part2.toycrimenotification.base.ViewState
import com.android.aop.part2.toycrimenotification.data.model.CriminalItem
import com.android.aop.part2.toycrimenotification.room.entity.CriminalEntity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

sealed class MapViewState : ViewState {

    object ShowProgress : MapViewState()
    object HideProgress : MapViewState()
    data class Error(val errorMessage: String) : MapViewState()
    object CallPolice : MapViewState()
    object WithdrawUser : MapViewState()
    object LogoutUser : MapViewState()
    object ShowUserPopupMenu : MapViewState()

    data class SetZoomLevel(val zoomLevel: Int) : MapViewState()
    data class SetCurrentLocation(val mapPoint: MapPoint) : MapViewState()
    data class AroundCriminals(val list: List<CriminalEntity>) : MapViewState()
    data class RenewCurrentLocation(val mapPoint: MapPoint) : MapViewState()

    data class GetCriminalItems(@Suppress("ArrayInDataClass") val items: Array<MapPOIItem>) :
        MapViewState()

    data class GetSelectPOIItem(val item: CriminalItem, val distance: String) : MapViewState()

    object RouteAroundCriminalList : MapViewState()
}

