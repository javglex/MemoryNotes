package com.sg.memorynotes.framework.di

import android.app.Application
import com.sg.core.repository.NoteRepository
import com.sg.memorynotes.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideRepository(app: Application) = NoteRepository(RoomNoteDataSource(app))
}