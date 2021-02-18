package com.sg.memorynotes.framework.di

import com.sg.core.repository.NoteRepository
import com.sg.core.usecase.AddNote
import com.sg.core.usecase.GetAllNotes
import com.sg.core.usecase.GetNote
import com.sg.core.usecase.RemoveNote
import com.sg.memorynotes.framework.UseCases
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun getUseCases(repository: NoteRepository) = UseCases(
            AddNote(repository),
            GetAllNotes(repository),
            GetNote(repository),
            RemoveNote(repository)
    )
}