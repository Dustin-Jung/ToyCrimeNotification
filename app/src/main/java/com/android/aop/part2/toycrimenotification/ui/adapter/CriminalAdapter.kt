package com.android.aop.part2.toycrimenotification.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.aop.part2.toycrimenotification.data.model.DistanceCriminal
import com.android.aop.part2.toycrimenotification.ui.adapter.viewholder.CriminalViewHolder

class CriminalAdapter: RecyclerView.Adapter<CriminalViewHolder>() {

    private val criminalList = mutableListOf<DistanceCriminal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CriminalViewHolder =
        CriminalViewHolder(parent)

    override fun onBindViewHolder(holder: CriminalViewHolder, position: Int) {
        holder.bind(criminalList[position])
    }

    override fun getItemCount(): Int =
        criminalList.size

    fun renewAll(list: List<DistanceCriminal>) {
        criminalList.clear()
        criminalList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear(){
        criminalList.clear()
        notifyDataSetChanged()
    }
}