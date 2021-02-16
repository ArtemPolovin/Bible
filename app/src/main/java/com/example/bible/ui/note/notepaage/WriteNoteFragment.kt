package com.example.bible.ui.note.notepaage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bible.App
import com.example.bible.R
import kotlinx.android.synthetic.main.fragment_write_note.*
import javax.inject.Inject

class WriteNoteFragment : Fragment() {

    @Inject
    lateinit var writeNoteFactory: WriteNoteFactory
    private lateinit var writeNoteViewModel: WriteNoteViewModel

    private lateinit var editNoteTitle: EditText
    private lateinit var editNoteText: EditText

    private lateinit var navController: NavController

    private var topicId: Long? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_write_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setActionBar()

        editNoteText = view.findViewById(R.id.edit_note_text)
        editNoteTitle = view.findViewById(R.id.edit_title_note_page)

        setUpNote()

        (activity?.applicationContext as App).bibleComponent.inject(this)

        writeNoteViewModel =
            ViewModelProvider(this, writeNoteFactory).get(WriteNoteViewModel::class.java)

        navController = Navigation.findNavController(view)


       // Log.i(".................","topicId = $topicId ; note = $note ; noteTitle = $noteTitle")



    }

    private fun setUpNote() {

        val note = arguments?.getString("note")
        val noteTitle = arguments?.getString("title")

        editNoteTitle.setText(noteTitle)
        editNoteText.setText(note)
    }

    private fun setActionBar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar_write_note)
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            getString(R.string.write_note_actionbar_title)
    }

    private fun saveNote() {

        topicId = arguments?.getLong("topicId")

        val id = arguments?.getInt("id")

        topicId?.let {topicId ->
            id?.let {id ->
                writeNoteViewModel.receiveCreatedNoteData(
                    editNoteText.text.toString(),
                    topicId,
                    editNoteTitle.text.toString(),
                    id
                )
            }

        }

    }

    private fun closeKeyboard() {
        activity?.currentFocus?.let {
            val imm: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.write_note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        saveNote()
        closeKeyboard()
        val bundle = bundleOf("topicId" to topicId)
        navController.navigate(R.id.action_writeNoteFragment_to_noteFragment, bundle)
        return true
    }
}