package com.app.func.view.recycler_view_custom.ravi_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.func.R
import com.app.func.base_content.DiffCallBack
import com.app.func.view.recycler_view_custom.ravi_recyclerview.CartListAdapter.MyViewHolder
import com.bumptech.glide.Glide

class CartListAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private var cartList = mutableListOf<ItemCart>()

    fun setDataAdapter(list: List<ItemCart>) {
        val diffResult = calculateDiff(list)
        cartList.clear()
        cartList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newList: List<ItemCart>): DiffUtil.DiffResult = DiffUtil.calculateDiff(
        DiffCallBack(
            oldList = cartList,
            newList = newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.id == newItem.id
            },
            areContentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    )
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var description: TextView = view.findViewById(R.id.description)
        var price: TextView = view.findViewById(R.id.price)
        var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        var viewBackground: RelativeLayout = view.findViewById(R.id.view_background)
        @JvmField
        var viewForeground: RelativeLayout = view.findViewById(R.id.view_foreground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (_, name, description, price, thumbnail) = cartList[position]
        holder.name.text = name
        holder.description.text = description
        holder.price.text = "₹$price"
        Glide.with(holder.itemView.context).load(thumbnail).into(holder.thumbnail)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun removeItem(position: Int) {
        cartList.removeAt(position)
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position)
    }

    fun restoreItem(item: ItemCart, position: Int) {
        cartList.add(position, item)
        // notify item added by position
        notifyItemInserted(position)
    }
}