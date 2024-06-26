package com.app.func.features.room_database_sqlite_mvvm.view

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.NoteHomeLayoutBinding
import com.app.func.features.room_database_sqlite_mvvm.adapters.NoteAdapter
import com.app.func.features.room_database_sqlite_mvvm.utils.ConstantsNote
import com.app.func.utils.DebugLog

class NoteHomeFragment : BaseFragment<NoteHomeLayoutBinding>() {

    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter

    override fun getViewBinding(): NoteHomeLayoutBinding {
        return NoteHomeLayoutBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        setUpRecyclerView()
        setUpListeners()
        noteViewModel.getAllNotes()?.observe(viewLifecycleOwner) {
            DebugLog.i("Notes observed ----- $it")
            noteAdapter.submitList(it)
        }
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }

    private fun setUpListeners() {
        binding?.buttonAddNote?.setOnClickListener {
//            val intent = Intent(this, AddEditNoteActivity::class.java)
//            startActivityForResult(intent, ADD_NOTE_REQUEST)
            findNavController().navigate(R.id.noteDeleteAddFragment)
        }

        // swipe listener
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = noteAdapter.getNoteAt(viewHolder.adapterPosition)
                noteViewModel.delete(note)
            }

        }).attachToRecyclerView(binding?.recyclerView)
    }

    private fun setUpRecyclerView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.setHasFixedSize(true)
        noteAdapter = NoteAdapter { clickedNote ->
//            val intent = Intent(this, AddEditNoteActivity::class.java)
//            intent.putExtra(ConstantsNote.EXTRA_ID, clickedNote.id)
//            intent.putExtra(ConstantsNote.EXTRA_TITLE, clickedNote.title)
//            intent.putExtra(ConstantsNote.EXTRA_DESCRIPTION, clickedNote.description)
//            intent.putExtra(ConstantsNote.EXTRA_PRIORITY, clickedNote.priority)
//            startActivityForResult(intent, EDIT_NOTE_REQUEST)
            val bundle = bundleOf(
                ConstantsNote.EXTRA_ID to clickedNote.id,
                ConstantsNote.EXTRA_TITLE to clickedNote.title,
                ConstantsNote.EXTRA_DESCRIPTION to clickedNote.description,
                ConstantsNote.EXTRA_PRIORITY to clickedNote.priority
            )
            findNavController().navigate(R.id.noteDeleteAddFragment, bundle)
        }
        binding?.recyclerView?.adapter = noteAdapter
    }
}