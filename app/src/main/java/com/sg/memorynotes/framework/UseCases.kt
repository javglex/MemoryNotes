package com.sg.memorynotes.framework

import com.sg.core.usecase.AddNote
import com.sg.core.usecase.GetAllNotes
import com.sg.core.usecase.GetNote
import com.sg.core.usecase.RemoveNote

data class UseCases(
        val addNote: AddNote, //done this way (actions as classes) for dependency injection
        val getAllNotes: GetAllNotes,
        val getNote: GetNote,
        val removeNote: RemoveNote
)