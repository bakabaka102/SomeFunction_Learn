package com.app.func.view.recycler_view_custom

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.func.view.recycler_view_custom.adapters.UserAdapter

class RecyclerViewItemTouchHelper(
    dragDirs: Int,
    swipeDirs: Int,
    private var itemTouchHelperListener: ItemTouchHelperListener
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        itemTouchHelperListener?.onSwipedView(viewHolder)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        val viewForeground = (viewHolder as UserAdapter.UserViewHolder).consItem
        getDefaultUIUtil().onSelected(viewForeground)
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val viewForeground = (viewHolder as UserAdapter.UserViewHolder).consItem
        getDefaultUIUtil().onDrawOver(
            c,
            recyclerView,
            viewForeground,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val viewForeground = (viewHolder as UserAdapter.UserViewHolder).consItem
        getDefaultUIUtil().onDraw(
            c,
            recyclerView,
            viewForeground,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }


    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val viewForeground = (viewHolder as UserAdapter.UserViewHolder).consItem
        getDefaultUIUtil().clearView(viewForeground)
    }
}