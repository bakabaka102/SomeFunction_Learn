package com.app.func.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentListUserBinding
import com.app.func.utils.MyToast
import com.app.func.view.recycler_view_custom.ItemTouchHelperListener
import com.app.func.view.recycler_view_custom.RecyclerViewItemTouchHelper
import com.app.func.view.recycler_view_custom.adapters.UserAdapter
import com.app.func.view.recycler_view_custom.models.User
import com.google.android.material.snackbar.Snackbar

class ListUserFragment : BaseFragment() {

    private var bindingListUserFragment: FragmentListUserBinding? = null
    private var mUsers = mutableListOf<User>()
    private var userAdapter = UserAdapter()
    private val listenerSwipeAction = object : ItemTouchHelperListener {
        override fun onSwipedViewToLeft(viewHolder: RecyclerView.ViewHolder) {
            actionSwipedViewToLeft(viewHolder)
        }

        override fun onSwipedViewToRight(viewHolder: RecyclerView.ViewHolder) {
            actionSwipedViewToRight(viewHolder)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingListUserFragment = FragmentListUserBinding.inflate(inflater, container, false)

        mUsers = getListUser()
        userAdapter.initData(mUsers)
        bindingListUserFragment?.recyclerViewUser?.adapter = userAdapter
        val liner = LinearLayoutManager(requireContext())
        bindingListUserFragment?.recyclerViewUser?.layoutManager = liner
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        bindingListUserFragment?.recyclerViewUser?.addItemDecoration(dividerItemDecoration)
        val simpleCallback =
            RecyclerViewItemTouchHelper(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
                listenerSwipeAction
            )
        ItemTouchHelper(simpleCallback).attachToRecyclerView(bindingListUserFragment?.recyclerViewUser)

        return bindingListUserFragment?.root
    }

    private fun getListUser(): MutableList<User> {
        val listUser = mutableListOf<User>()
        for (i in 0 until 20) {
            listUser.add(User(id = i, name = "User ${i + 1}"))
        }
        return listUser
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingListUserFragment = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = ListUserFragment()
    }


    private fun actionSwipedViewToLeft(viewHolder: RecyclerView.ViewHolder) {
        MyToast.showToast(requireContext(), "${viewHolder.adapterPosition}")
        if (viewHolder is UserAdapter.UserViewHolder) {
            val indexDelete = viewHolder.adapterPosition
            val userRemove = mUsers[indexDelete]
            val nameRemove = userRemove.name
            userAdapter.removeUser(indexDelete)

            val notify = "You remove: $nameRemove at position: ${indexDelete + 1}"
            val snackbar = bindingListUserFragment?.consRootView?.let {
                Snackbar.make(it, notify, Snackbar.LENGTH_LONG)
            }
            snackbar?.setAction("Undo") {
                userAdapter.undoRemoveUser(indexDelete, userRemove)
                if (indexDelete == 0 || indexDelete == mUsers.size - 1) {
                    bindingListUserFragment?.recyclerViewUser?.scrollToPosition(indexDelete)
                }
            }
            snackbar?.setActionTextColor(Color.RED)
            snackbar?.show()
        }
    }

    private fun actionSwipedViewToRight(viewHolder: RecyclerView.ViewHolder) {
        MyToast.showToast(requireContext(), "Swipe to right.! --- ${viewHolder.adapterPosition}")
    }
}