package com.app.func.features.room_coroutines.views

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.base_content.WordsApplication
import com.app.func.databinding.NoteHomeLayoutBinding
import com.app.func.features.room_coroutines.WordViewModel
import com.app.func.features.room_coroutines.WordViewModelFactory
import com.app.func.features.room_coroutines.adapters.WordListAdapter

class MainRoomCoroutinesFragment : BaseFragment<NoteHomeLayoutBinding>() {

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((activity?.application as WordsApplication).repository)
    }

    override fun getViewBinding(): NoteHomeLayoutBinding {
        return NoteHomeLayoutBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        binding?.buttonAddNote?.setOnClickListener {
            findNavController().navigate(R.id.addNewWordFragment)
        }

        val wordListAdapter = WordListAdapter {

        }
        binding?.recyclerView?.adapter = wordListAdapter
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        wordViewModel.allWords.observe(viewLifecycleOwner) {
            it.let { wordListAdapter.submitList(it) }
        }
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }
}

