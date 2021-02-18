package com.sg.memorynotes.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sg.core.data.Note

@Entity(tableName = "note")
data class NoteEntity(
    var title: String,
    var content: String,
    @ColumnInfo(name = "creation_date") //override column name for database
    var creationTime: Long,
    @ColumnInfo(name = "update_time")
    var updateTime: Long,
    @PrimaryKey(autoGenerate = true)     //we use our id long as primary key
    var id: Long = 0L
){

    companion object {
        fun fromNote(note: Note) = NoteEntity(note.title, note.content, note.creationTime, note.updateTime, note.id)
    }

    fun toNote() = Note(title, content, creationTime, updateTime, id)

}