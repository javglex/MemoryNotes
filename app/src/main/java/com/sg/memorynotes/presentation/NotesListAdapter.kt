package com.sg.memorynotes.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sg.core.data.Note
import com.sg.memorynotes.R
import kotlinx.android.synthetic.main.item_note.view.*
import java.text.SimpleDateFormat
import java.util.*

class NotesListAdapter(var notes: ArrayList<Note>, val actions: ListAction): RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder = NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int){
        holder.bind(notes.get(position))
    }

    override fun getItemCount(): Int = notes.size

    inner class NoteViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val layout = view.noteItemLayout
        private val noteTitle = view.note_title
        private val noteContent = view.content
        private val noteDate = view.date
        private val noteWords = view.word_count

        fun bind(note: Note){
            noteTitle.text = note.title
            noteContent.text = note.content

            val sdf = SimpleDateFormat("MMM dd, HH:mm:ss")
            val resultDate = Date(note.updateTime)
            noteDate.text = "Last updated: ${sdf.format(resultDate)}"
            noteWords.text = "Words: ${note.wordCount}"

            layout.setOnClickListener {
                actions.onClick(note.id)
            }

        }
    }
}