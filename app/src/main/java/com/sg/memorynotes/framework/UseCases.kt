package com.sg.memorynotes.framework

import com.sg.core.usecase.*

data class UseCases(
        val addNote: AddNote, //done this way (actions as classes) for dependency injection
        val getAllNotes: GetAllNotes,
        val getNote: GetNote,
        val removeNote: RemoveNote,
        val getWordCount: GetWordCount
)