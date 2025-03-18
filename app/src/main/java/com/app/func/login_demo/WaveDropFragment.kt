package com.app.func.login_demo

import android.graphics.Color
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.func.base_content.BaseFragment
import com.app.func.coroutine_demo.retrofit.aaa.MyViewModelFactory
import com.app.func.coroutine_demo.retrofit.base.ApiConstants
import com.app.func.databinding.FragmentWaveDropBinding
import com.app.func.view.recycler_view_custom.ravi_recyclerview.CartListAdapter
import com.app.func.view.recycler_view_custom.ravi_recyclerview.CartListAdapter.MyViewHolder
import com.app.func.view.recycler_view_custom.ravi_recyclerview.ItemCart
import com.app.func.view.recycler_view_custom.ravi_recyclerview.RecyclerItemTouchHelper
import com.google.android.material.snackbar.Snackbar

class WaveDropFragment : BaseFragment<FragmentWaveDropBinding>(),
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    override fun getViewBinding() = FragmentWaveDropBinding.inflate(layoutInflater)

    override fun setUpViews() {
        mViewModel = ViewModelProvider(
            this,
            MyViewModelFactory(getRepositoryRetrofit(ApiConstants.BASE_URL_FOOD))
        )[SignUpViewModel::class.java]

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding?.recyclerView?.layoutManager = mLayoutManager
        binding?.recyclerView?.itemAnimator = DefaultItemAnimator()
        binding?.recyclerView?.addItemDecoration(dividerItemDecoration)
        binding?.recyclerView?.adapter = mAdapter

        mViewModel?.getListMenuFood()

        initObservers()
        mViewModel?.loading?.observe(viewLifecycleOwner) {
            binding?.progressBar?.isVisible = it
        }


        /* adding item touch helper
         only ItemTouchHelper.LEFT added to detect Right to Left swipe
         if you want both Right -> Left and Left -> Right
         add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param*/
        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding?.recyclerView)

        //prepareCart()
        val itemTouchHelperCallback1 = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

        }

        //attaching the touch helper to recycler view
        ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(binding?.recyclerView)
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }

    private val cartList = mutableListOf<ItemCart>()
    private var mAdapter = CartListAdapter()
    private var mViewModel: SignUpViewModel? = null

    private fun initObservers() {
        mViewModel?.menuFoodList?.observe(viewLifecycleOwner) {
            mAdapter.setDataAdapter(it.body() ?: emptyList())
        }

        observerError()
    }

    private fun observerError() {
        mViewModel?.errorMessage?.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        if (viewHolder is MyViewHolder) {
            // get the removed item name to display it in snack bar
            val name = cartList[viewHolder.adapterPosition].name
            // backup of removed item for undo purpose
            val deletedItem = cartList[viewHolder.adapterPosition]
            val deletedIndex = viewHolder.adapterPosition

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.adapterPosition)

            // showing snack bar with Undo option
            val snackbar = binding?.scrollViewSignUp?.let {
                Snackbar.make(it, "$name removed from cart!", Snackbar.LENGTH_LONG)
            }
            snackbar?.setAction("Undo") { // undo is selected, restore the deleted item
                mAdapter.restoreItem(deletedItem, deletedIndex)
            }
            snackbar?.setActionTextColor(Color.YELLOW)
            snackbar?.show()
        }
    }
}