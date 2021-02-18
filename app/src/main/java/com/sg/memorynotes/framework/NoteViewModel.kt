package com.sg.memorynotes.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sg.core.data.Note
import com.sg.core.repository.NoteRepository
import com.sg.core.usecase.AddNote
import com.sg.core.usecase.GetAllNotes
import com.sg.core.usecase.GetNote
import com.sg.core.usecase.RemoveNote
import com.sg.memorynotes.framework.di.ApplicationModule
import com.sg.memorynotes.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO) //IO useful when communicating with another data source, db/network etc
    @Inject
    lateinit var useCases: UseCases
    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note?>()

    init {
        DaggerViewModelComponent.builder()
                .applicationModule(ApplicationModule(getApplication()))
                .build()
                .inject(this)
    }

    fun saveNote(note: Note) {
        coroutineScope.launch {
            useCases.addNote(note)
            saved.postValue(true) //waits for response from db
        }
    }

    fun getNote(id: Long){
        coroutineScope.launch {
            var note: Note? = useCases.getNote(id)
            currentNote.postValue(note) //waits for response from db
        }
    }

    fun deleteNote(note: Note) {
        coroutineScope.launch {
            useCases.removeNote(note)
            saved.postValue(true)
        }
    }

}