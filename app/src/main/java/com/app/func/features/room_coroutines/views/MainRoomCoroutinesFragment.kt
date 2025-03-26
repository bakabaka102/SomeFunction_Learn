package com.app.func.features.room_coroutines.views

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.NoteHomeLayoutBinding
import com.app.func.features.room_coroutines.Word
import com.app.func.features.room_coroutines.WordViewModel
import com.app.func.features.room_coroutines.adapters.WordListAdapter
import com.app.func.utils.MyToast

class MainRoomCoroutinesFragment : BaseFragment<NoteHomeLayoutBinding>() {
    private val wordViewModel: WordViewModel by viewModels()
    private var word: Word? = null
    lateinit var wordListAdapter: WordListAdapter

    override fun getViewBinding() = NoteHomeLayoutBinding.inflate(layoutInflater)

    override fun setUpViews() {
        wordListAdapter = WordListAdapter {
            word = it
            binding?.editTextWord?.setText(it.word)
        }
        binding?.recyclerviewUsers?.apply {
            adapter = wordListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        wordViewModel.getAllWordsInDB()
    }

    override fun observeView() {
        wordViewModel.allWords.observe(viewLifecycleOwner) {
            wordListAdapter.submitList(it)
        }
    }

    override fun observeData() {

    }

    override fun initActions() {
        binding?.btnAdd?.setOnClickListener {
            val word = binding?.editTextWord?.text.toString().trim()
            if (word.isNotBlank() || word.isNotEmpty()) {
                wordViewModel.insert(Word(word = word))
            } else {
                MyToast.showToast(requireContext(), "Input in valid")
            }
        }

        binding?.btnUpdate?.setOnClickListener {

        }
    }
}

