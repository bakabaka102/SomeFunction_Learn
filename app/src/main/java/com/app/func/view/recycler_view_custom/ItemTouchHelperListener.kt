package com.app.func.view.recycler_view_custom

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperListener {

    fun onSwipedView(viewHolder: RecyclerView.ViewHolder)
}