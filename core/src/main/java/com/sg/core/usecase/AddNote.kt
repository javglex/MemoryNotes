package com.sg.core.usecase

import com.sg.core.data.Note
import com.sg.core.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) = noteRepository.addNote(note)

}