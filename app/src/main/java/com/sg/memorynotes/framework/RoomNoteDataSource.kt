package com.sg.memorynotes.framework

import android.content.Context
import com.sg.core.data.Note
import com.sg.core.repository.NoteDataSource
import com.sg.memorynotes.framework.db.DatabaseService
import com.sg.memorynotes.framework.db.NoteEntity

class RoomNoteDataSource(context: Context): NoteDataSource {

    val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long): Note? = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll(): List<Note> = noteDao.getAllNoteEntities().map{ it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNote(NoteEntity.fromNote(note))

}