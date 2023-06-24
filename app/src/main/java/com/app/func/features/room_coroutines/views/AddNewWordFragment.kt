package com.app.func.features.room_coroutines.views

import androidx.fragment.app.viewModels
import com.app.func.base_content.BaseFragment
import com.app.func.base_content.WordsApplication
import com.app.func.databinding.AddNewWordFragmentBinding
import com.app.func.features.room_coroutines.Word
import com.app.func.features.room_coroutines.WordViewModel
import com.app.func.features.room_coroutines.WordViewModelFactory
import com.app.func.utils.MyToast

class AddNewWordFragment : BaseFragment<AddNewWordFragmentBinding>() {

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((activity?.application as WordsApplication).repository)
    }

    override fun getViewBinding(): AddNewWordFragmentBinding {
        return AddNewWordFragmentBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        binding?.buttonSave?.setOnClickListener {
            val word: String = binding?.editWord?.text.toString().trim()
            if (word.isNotBlank() && word.isNotEmpty()) {
                wordViewModel.insert(Word(word = word))
            } else {
                MyToast.showToast(requireContext(), "Input in valid")
            }
        }
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }

}

