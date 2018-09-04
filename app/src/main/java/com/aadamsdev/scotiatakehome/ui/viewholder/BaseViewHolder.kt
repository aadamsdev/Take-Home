package com.aadamsdev.scotiatakehome.ui.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.aadamsdev.scotiatakehome.repository.entity.BaseListItem

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(listItem: BaseListItem)
}