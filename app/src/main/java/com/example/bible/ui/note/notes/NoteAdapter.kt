package com.example.bible.ui.note.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bible.R
import com.example.domain.models.NoteModel

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private val notesList = mutableListOf<NoteModel>()

    private lateinit var onClickNoteListener: OnClickNoteListener

    fun setNoteList(newNoteList: List<NoteModel>) {
        notesList.clear()
        notesList.addAll(newNoteList)
        notifyDataSetChanged()
    }

    fun onClickNoteListener(onClickNoteListener: OnClickNoteListener) {
        this.onClickNoteListener = onClickNoteListener
    }

    fun getNoteId(itemPosition: Int) = notesList[itemPosition].noteId


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_note, parent, false)
        return NoteHolder(view, onClickNoteListener)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val noteModel = notesList[position]
        holder.bind(noteModel)
        holder.onClickItem(noteModel)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class NoteHolder(itemView: View, private val onClickNoteListener: OnClickNoteListener) :
        RecyclerView.ViewHolder(itemView) {

        private val cellText = itemView.findViewById<TextView>(R.id.text_cell_note)
        private val cellNoteTitle = itemView.findViewById<TextView>(R.id.text_title_note)

        fun bind(note: NoteModel) {
            cellText.text = note.note
            cellNoteTitle.text = note.noteTitle
        }

        fun onClickItem(noteModel: NoteModel) {
            itemView.setOnClickListener {
                onClickNoteListener.getNoteInfo(
                    noteModel.note,
                    noteModel.noteTitle,
                    noteModel.id
                )
            }
        }
    }

    interface OnClickNoteListener {
        fun getNoteInfo(note: String, noteTitle: String?, id: Int)
    }

}