package com.app.func.features.room_coroutines.views

import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentMainRoomCoroutineBinding
import com.app.func.features.room_coroutines.Word
import com.app.func.features.room_coroutines.WordViewModel
import com.app.func.features.room_coroutines.adapters.RestoreRemoveListener
import com.app.func.features.room_coroutines.adapters.SwipeToDeleteCallback
import com.app.func.features.room_coroutines.adapters.WordListAdapter
import com.app.func.utils.MyToast
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainRoomCoroutinesFragment :
    BaseFragment<FragmentMainRoomCoroutineBinding>(), RestoreRemoveListener {

    private val viewModel: WordViewModel by viewModels()
    private var word: Word? = null
    private var listData: MutableList<Word> = mutableListOf()
    lateinit var wordListAdapter: WordListAdapter

    override fun getViewBinding() = FragmentMainRoomCoroutineBinding.inflate(layoutInflater)

    override fun setUpViews() {
        wordListAdapter = WordListAdapter {
            word = it
            binding?.editTextWord?.setText(it.word)
            binding?.btnUpdate?.isEnabled = true
        }
        binding?.recyclerviewUsers?.apply {
            adapter = wordListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(wordListAdapter, this))
        itemTouchHelper.attachToRecyclerView(binding?.recyclerviewUsers)
        wordListAdapter.onItemRemove = {
            viewModel.delete(it)
        }

        //viewModel.getAllWordsInDB()
    }

    override fun observeView() {

        /*viewModel.wordState.observe(viewLifecycleOwner) {
            when(it) {
                is DataResult.Loading -> {
                    binding?.layoutLoading?.isVisible = true
                }
                is DataResult.Success -> {
                    binding?.layoutLoading?.isVisible = false
                    MyToast.showToast(requireContext(), "Success")
                    wordListAdapter.submitList(it.data)
                }
                is DataResult.Error -> {
                    binding?.layoutLoading?.isVisible = false
                    binding?.errorText?.text = it.throwable.message.toString()
                }
            }
        }*/

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allWords.collect {
                    wordListAdapter.submitList(it)
                    listData = it.toMutableList()
                }
            }
        }

    }

    override fun observeData() {

    }

    override fun initActions() {
        binding?.btnAdd?.setOnClickListener {
            val word = binding?.editTextWord?.text.toString().trim()
            if (word.isNotBlank() || word.isNotEmpty()) {
                if (viewModel.insert(Word(word = word)) > 0) {
                    MyToast.showToast(requireContext(), "Insert success")
                } else {
                    MyToast.showToast(requireContext(), "Insert failed")
                }
            } else {
                MyToast.showToast(requireContext(), "Input in valid")
            }
            clearText()
        }

        binding?.btnUpdate?.setOnClickListener {
            word?.let {
                it.word = binding?.editTextWord?.text.toString().trim()
                if (viewModel.update(it) > 0) {
                    MyToast.showToast(requireContext(), "Update success")
                    binding?.btnUpdate?.isEnabled = false
                    clearText()
                } else {
                    MyToast.showToast(requireContext(), "Update failed")
                }
            }
        }
    }

    private fun clearText() {
        binding?.editTextWord?.text?.clear()
    }

    override fun onRestoreClick(word: Word) {
        val notify = "You remove: $word}"
        val snackBar = binding?.constraintParent?.let {
            Snackbar.make(it, notify, Snackbar.LENGTH_LONG)
        }
        snackBar?.apply {
            setAction("Undo") {
                wordListAdapter.restoreItem()
                viewModel.insert(word)
            }
            setActionTextColor(Color.RED)
            show()
        }
    }
}

