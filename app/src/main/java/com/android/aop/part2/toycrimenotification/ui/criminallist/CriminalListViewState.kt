package com.android.aop.part2.toycrimenotification.ui.criminallist

import com.android.aop.part2.toycrimenotification.base.ViewState
import com.android.aop.part2.toycrimenotification.data.model.DistanceCriminal

sealed class CriminalListViewState : ViewState {
    object ShowProgress : CriminalListViewState()
    object HideProgress : CriminalListViewState()
    object EmptyCriminalList : CriminalListViewState()
    data class RenewCriminalList(val list: List<DistanceCriminal>) : CriminalListViewState()
    data class Error(val errorMessage: String) : CriminalListViewState()
}