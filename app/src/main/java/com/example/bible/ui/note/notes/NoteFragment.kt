package com.example.bible.ui.note.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bible.App
import com.example.bible.R
import com.example.bible.ui.viewstate.NoteViewState
import kotlinx.android.synthetic.main.fragment_note.*
import javax.inject.Inject

class NoteFragment : Fragment() {

    @Inject
    lateinit var noteFactory: NoteFactory
    private lateinit var noteViewModel: NoteViewModel

    private lateinit var adapter: NoteAdapter

    private lateinit var navController: NavController

    private var topicId: Long? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setActionBar()

        (activity?.applicationContext as App).bibleComponent.inject(this)

        noteViewModel = ViewModelProvider(this, noteFactory).get(NoteViewModel::class.java)

        navController = Navigation.findNavController(view)

        adapter = NoteAdapter()

        rv_note.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_note.adapter = adapter


        topicId = arguments?.getLong("topicId")
        topicId?.let {
            noteViewModel.receiveTopicId(it)
            editNote(it)
        }

        add_note_floating_button.setOnClickListener {
            topicId?.let { openWriteNotePage(it) }

        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv_note)

        setUpNoteList()
        refreshTopics()

    }

    private fun setUpNoteList() {
        noteViewModel.noteList.observe(viewLifecycleOwner, {

            when (it) {
                NoteViewState.Loading -> pull_refresh_notes.isRefreshing = true
                NoteViewState.Error -> edit_error.visibility = View.VISIBLE
                is NoteViewState.NotesLoaded -> {
                    pull_refresh_notes.isRefreshing = false
                    edit_error.visibility = View.GONE
                    adapter.setNoteList(it.notesList)
                }

            }
        })
    }

    private fun editNote(topicId: Long) {
        adapter.onClickNoteListener(object : NoteAdapter.OnClickNoteListener {
            override fun getNoteInfo(note: String, noteTitle: String?, id: Int) {
                val bundle = bundleOf("title" to noteTitle, "note" to note, "topicId" to topicId, "id" to id)
                navController.navigate(R.id.action_noteFragment_to_writeNoteFragment, bundle)
            }

        })
    }

    private fun openWriteNotePage(topicId: Long) {
        val bundle = bundleOf("topicId" to topicId)
        navController.navigate(R.id.action_noteFragment_to_writeNoteFragment, bundle)
    }

    private fun setActionBar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar_note_list)
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            getString(R.string.note_list_actionbar_title)
    }

    private fun refreshTopics() {
        pull_refresh_notes.setOnRefreshListener {
            topicId?.let { noteViewModel.refreshNoteList(it) }

        }
    }

    private val itemTouchHelperCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterPosition = viewHolder.adapterPosition
                val noteId = adapter.getNoteId(adapterPosition)
                noteViewModel.receiveRemovedNoteId(noteId)
            }

        }


}
