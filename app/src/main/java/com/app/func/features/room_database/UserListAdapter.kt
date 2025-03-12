package com.app.func.features.room_database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.func.base_content.DiffCallBack
import com.app.func.databinding.ItemUserListBinding

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    var userList = mutableListOf<User>()
    var clickListener: ListClickListener<User>? = null
    var onDeleteItem: ((User) -> Unit)? = null

    fun setUsers(users: List<User>) {
        val diffResult = calculateDiff(users)
        userList.clear()
        userList.addAll(users)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClick(listClickListener: ListClickListener<User>) {
        this.clickListener = listClickListener
    }

    private fun calculateDiff(newList: List<User>): DiffUtil.DiffResult = DiffUtil.calculateDiff(
        DiffCallBack(
            oldList = userList,
            newList = newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.userId == newItem.userId
            },
            areContentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    )

    inner class UserViewHolder(val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindDataToViews(position: Int) {
            val user = userList[position]
            val userName = "${user.userId} - ${user.userName}"
            binding.textUsername.text = userName
            binding.textLocation.text = user.location
            binding.textEmail.text = user.email

            binding.layout.setOnClickListener {
                clickListener?.onClick(user, position)
            }

            binding.imgDelete.setOnClickListener {
                //clickListener?.onDelete(user)
                onDeleteItem?.invoke(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserListBinding.inflate(layoutInflater, parent, false)

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindDataToViews(position)
    }

    override fun getItemCount(): Int = userList.size
}

interface ListClickListener<T> {
    fun onClick(data: T, position: Int)
    //fun onDelete(user: T)
}