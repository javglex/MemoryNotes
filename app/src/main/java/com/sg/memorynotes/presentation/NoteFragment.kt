package com.sg.memorynotes.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.sg.core.data.Note
import com.sg.memorynotes.R
import com.sg.memorynotes.framework.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*


class NoteFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("","",0L,0L)
    private var noteId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteid
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        if (noteId != 0L)
            viewModel.getNote(noteId)

        fab_save_btn.setOnClickListener {
            if (edit_title_view.text.toString() != "" || edit_content_view.text.toString() != ""){
                val time: Long = System.currentTimeMillis()
                currentNote.title = edit_title_view.text.toString()
                currentNote.content = edit_content_view.text.toString()
                currentNote.updateTime = time
                if (currentNote.id == 0L) { //if note doesnt exists set creation time
                    currentNote.creationTime = time
                }
                viewModel.saveNote(currentNote)
            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.saved.observe(this, Observer {
            if (it) {
                Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(edit_title_view).popBackStack()
            } else {
                Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.currentNote.observe(this, Observer { note ->
            note?.let {
                currentNote = it
                edit_title_view.setText(it.title, TextView.BufferType.EDITABLE)
                edit_content_view.setText(it.content, TextView.BufferType.EDITABLE)
            }
        })
    }
    
    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(edit_title_view.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.menu_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteNote -> {
                if (context != null && noteId != 0L) {
                    AlertDialog.Builder(context!!)
                            .setTitle("Delete Note")
                            .setMessage("U sure u wanna do this?")
                            .setPositiveButton("Yes") { dialogInterface, i ->
                                viewModel.deleteNote(currentNote)
                            }
                            .setNegativeButton("Cancel"){ dialogInterface, i ->  }
                            .create()
                            .show()
                }
            }
        }
        return true;
    }

}