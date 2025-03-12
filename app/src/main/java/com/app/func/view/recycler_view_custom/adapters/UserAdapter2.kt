package com.app.func.view.recycler_view_custom.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.func.base_content.DiffCallBack
import com.app.func.databinding.ItemUser2Binding
import com.app.func.view.recycler_view_custom.models.User

class UserAdapter2 : RecyclerView.Adapter<UserAdapter2.UserViewHolder>() {

    private var listUser = mutableListOf<User>()

    fun submitData(users: List<User>) {
        val diffResult = calculateDiff(users)
        listUser.clear()
        listUser.addAll(users)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeUser(position: Int) {
        val newList = listUser.toMutableList()
        newList.removeAt(position)
        val diffResult = calculateDiff(newList)
        listUser = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun undoRemoveUser(position: Int, user: User) {
        val newList = listUser.toMutableList()
        newList.add(position, user)
        val diffResult = calculateDiff(newList)
        listUser = newList
        diffResult.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newList: List<User>): DiffUtil.DiffResult = DiffUtil.calculateDiff(
        DiffCallBack(
            oldList = listUser,
            newList = newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.id == newItem.id
            },
            areContentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    )

    inner class UserViewHolder(val binding: ItemUser2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        val consItem2 = binding.consItem2

        fun bindDataToView(user: User) {
            binding.textNameUser2.text = user.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemUser2Binding = ItemUser2Binding.inflate(layoutInflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]
        holder.bindDataToView(user)
    }

    override fun getItemCount(): Int = listUser.size
}