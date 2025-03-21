package com.app.func.coroutine_demo.retrofit.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.func.base_content.DiffCallBack
import com.app.func.networks.User
import com.app.func.databinding.ItemUserLayoutBinding
import com.bumptech.glide.Glide

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var listResult = mutableListOf<User>()

    fun setData(results: List<User>) {
        val diffResult = calculateDiff(results)
        this.listResult.clear()
        this.listResult.addAll(results)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newList: List<User>): DiffUtil.DiffResult = DiffUtil.calculateDiff(
        DiffCallBack(
            oldList = listResult,
            newList = newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.id == newItem.id
            },
            areContentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    )

    inner class UserViewHolder(val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViews(user: User) {
            binding.textViewUserName.text = user.name
            binding.textViewUserEmail.text = user.email
            Glide.with(binding.imageViewAvatar.context).load(user.avatar)
                .into(binding.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserLayoutBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindViews(listResult[position])
    }

    override fun getItemCount(): Int = listResult.size
}