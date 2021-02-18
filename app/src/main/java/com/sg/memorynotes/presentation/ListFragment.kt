package com.sg.memorynotes.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.sg.memorynotes.R
import com.sg.memorynotes.framework.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment(), ListAction {

    private val notesListAdapter = NotesListAdapter(arrayListOf(), this)
    private lateinit var viewModel: ListViewModel

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        observeViewModel()

        list_notes_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesListAdapter
        }

        fab_add_note.setOnClickListener{ it ->
            goToNoteDetails()
        }
    }

    private fun observeViewModel(){
        viewModel.notesList.observe(this, Observer{ notes ->
            progress_loading.visibility = View.GONE
            list_notes_view.visibility = View.VISIBLE
            notesListAdapter.updateNotes(notes.sortedByDescending { it.updateTime })
        })
    }

    private fun goToNoteDetails(id: Long = 0L) {
        val action : NavDirections = ListFragmentDirections.actionListFragmentToNoteFragment(id)
        Navigation.findNavController(list_notes_view).navigate(action)
    }

    override fun onResume(){
        super.onResume()
        viewModel.getNotes()
    }

    override fun onClick(id: Long) {
        goToNoteDetails(id)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}