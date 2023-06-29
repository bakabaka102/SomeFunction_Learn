package com.app.func.login_demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.RoomFragmentBinding
import com.app.func.features.room_database.ListClickListener
import com.app.func.features.room_database.User
import com.app.func.features.room_database.UserListAdapter
import com.app.func.features.room_database.UserRepository

class RoomFragment : BaseFragment<RoomFragmentBinding>() {

    private var userAdapter = UserListAdapter()
    private var mViewModel: ProfileViewModel? = null
    private val repository: UserRepository by lazy {
        UserRepository(requireContext())
    }
    var user: User? = null

    override fun getViewBinding(): RoomFragmentBinding = RoomFragmentBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel =
            ViewModelProvider(this, RoomDBFactory(repository))[ProfileViewModel::class.java]
    }

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
                    repository.deleteUser(it)
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
        mViewModel?.getUsersWithAsc?.observe(viewLifecycleOwner) {
            userAdapter.setUsers(it)
        }
    }

    override fun initActions() {

    }

    private fun fetchUsers() {
        val allUsers = repository.getAllUsers()
        allUsers?.let { userAdapter.setUsers(it) }
    }

    private fun updateUser() {
        if (isNotEmptyData()) {
            val user = User(
                userId = user?.userId,
                userName = binding?.edUsername?.text.toString(),
                location = binding?.edLocation?.text.toString(),
                email = binding?.edEmail?.text.toString()
            )
            repository.updateUser(user)
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
            repository.insertUser(user)
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