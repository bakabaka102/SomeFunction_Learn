package com.app.func.view.recycler_view_custom.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.func.base_content.DiffCallBack
import com.app.func.databinding.ItemUserBinding
import com.app.func.view.recycler_view_custom.models.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var listUser = mutableListOf<User>()

    fun initData(users: List<User>) {
        val diffResult = calculateDiff(users)
        listUser.clear()
        listUser.addAll(users)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeUser(position: Int) {
        listUser.removeAt(position)
        notifyItemRemoved(position)
    }

    fun undoRemoveUser(position: Int, user: User) {
        listUser.add(position, user)
        notifyItemInserted(position)
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

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindDataToView(user: User) {
            binding.textNameUser.text = user.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemUserBinding = ItemUserBinding.inflate(layoutInflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]
        holder.bindDataToView(user)
    }

    override fun getItemCount(): Int = listUser.size

}