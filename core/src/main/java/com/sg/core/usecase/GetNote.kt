package com.sg.core.usecase

import com.sg.core.data.Note
import com.sg.core.repository.NoteRepository

class GetNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(id: Long) = noteRepository.getNote(id)
}