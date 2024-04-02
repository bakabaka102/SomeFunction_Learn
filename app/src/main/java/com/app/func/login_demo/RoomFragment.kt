package com.app.func.login_demo

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.RoomFragmentBinding
import com.app.func.features.room_database.ListClickListener
import com.app.func.features.room_database.User
import com.app.func.features.room_database.UserListAdapter

class RoomFragment : BaseFragment<RoomFragmentBinding>() {

    private var userAdapter = UserListAdapter()
    private val mViewModel: ProfileViewModel by viewModels()
    var user: User? = null

    override fun getViewBinding(): RoomFragmentBinding = RoomFragmentBinding.inflate(layoutInflater)

    override fun setUpViews() {
        binding?.btnAdd?.setOnClickListener {
            addNewUser()
        }

        binding?.btnUpdate?.setOnClickListener {
            updateUser()
        }

        userAdapter.setOnItemClick(object : ListClickListener<User> {
            override fun onClick(data: User, position: Int) {
                //binding?.edUsername?.text = data.userName
                binding?.edUsername?.setText(data.userName)
                //binding?.edEmail?.text = data.email
                binding?.edEmail?.setText(data.email)
                //binding?.edLocation?.text = data.location
                binding?.edLocation?.setText(data.location)
                user = data
                binding?.btnUpdate?.isEnabled = true
            }

            /*override fun onDelete(user: User) {
                AlertDialog.Builder(context)
                    .setTitle("Delete user")
                    .setMessage("Are you sure you want to delete this user?")
                    .setPositiveButton("Yes") { _, _ ->
                        repository.deleteUser(user)
                    }
                    .setNegativeButton("No", null)
                    .setIcon(R.drawable.ic_white_delete)
                    .show()
            }*/
        })

        userAdapter.onDeleteItem = {
            AlertDialog.Builder(requireActivity())
                .setTitle("Delete user")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Yes") { _, _ ->
                    mViewModel.deleteUser(it)
                }
                .setNegativeButton("No", null)
                .setIcon(R.drawable.ic_white_delete)
                .show()
        }
        binding?.recyclerviewUsers?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerviewUsers?.adapter = userAdapter
    }

    override fun observeView() {

    }

    override fun observeData() {
        mViewModel.getUsersWithAsc?.observe(viewLifecycleOwner) {
            userAdapter.setUsers(it)
        }
    }

    override fun initActions() {

    }

    private fun updateUser() {
        if (isNotEmptyData()) {
            val user = User(
                userId = user?.userId,
                userName = binding?.edUsername?.text.toString(),
                location = binding?.edLocation?.text.toString(),
                email = binding?.edEmail?.text.toString()
            )
            mViewModel.updateUser(user)
        } else {
            Toast.makeText(requireContext(), "Invalid Input", Toast.LENGTH_SHORT).show()
        }
        binding?.btnUpdate?.isEnabled = false
    }

    private fun addNewUser() {
        if (isNotEmptyData()) {
            val user = User(
                userName = binding?.edUsername?.text.toString(),
                location = binding?.edLocation?.text.toString(),
                email = binding?.edEmail?.text.toString()
            )
            mViewModel.insertUser(user)
        } else {
            Toast.makeText(requireContext(), "Invalid Input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNotEmptyData(): Boolean {
        return (binding?.edUsername?.text?.isNotEmpty() == true
                && binding?.edEmail?.text?.isNotEmpty() == true
                && binding?.edLocation?.text?.isNotEmpty() == true)
    }

}