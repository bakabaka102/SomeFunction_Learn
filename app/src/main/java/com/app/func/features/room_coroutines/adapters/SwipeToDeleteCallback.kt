package com.app.func.features.room_coroutines.adapters

import android.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(
    private val adapter: WordListAdapter,
    private val restoreRemoveListener: RestoreRemoveListener,
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val item = adapter.words[position]
        AlertDialog.Builder(viewHolder.itemView.context)
            .setTitle("Delete it?")
            .setMessage("Are you sure you want to delete ${item.id} - ${item.word}?")
            .setPositiveButton("Yes") { dialog, _ ->
                adapter.removeWord(item)
                dialog.dismiss()
                restoreRemoveListener.onRestoreClick(item)
            }.setNegativeButton("No") { dialog, _ ->
                adapter.notifyItemChanged(position)
                dialog.dismiss()
            }.create()
            .show()
    }
}