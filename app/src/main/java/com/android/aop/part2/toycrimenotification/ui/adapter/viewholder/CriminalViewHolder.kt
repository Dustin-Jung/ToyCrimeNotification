package com.android.aop.part2.toycrimenotification.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.aop.part2.toycrimenotification.R
import com.android.aop.part2.toycrimenotification.data.model.DistanceCriminal
import com.android.aop.part2.toycrimenotification.databinding.ItemCriminalListBinding

class CriminalViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_criminal_list, parent, false)
) {
    private val binding = ItemCriminalListBinding.bind(itemView)

    fun bind(
        item: DistanceCriminal
    ) {
        binding.item = item
    }
}