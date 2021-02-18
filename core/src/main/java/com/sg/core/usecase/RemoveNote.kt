package com.sg.core.usecase

import com.sg.core.data.Note
import com.sg.core.repository.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.removeNote(note)
}