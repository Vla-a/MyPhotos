package com.example.myphotos.repositorues

import com.example.myphotos.data.Note
import com.example.myphotos.database.NoteDao
import com.example.myphotos.database.NotePhotoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepository (
    private val noteDao: NoteDao
) {

    fun getNoteList(): Flow<List<Note>> =
        noteDao.getsNoteList().map {
            it.map {
                Note(it.id, it.title, it.date, it.userPhoto)
            }
        }

    suspend fun addNote(note: NotePhotoEntity) {
        noteDao.addNote(note)
    }

    suspend fun deleteNote(note: NotePhotoEntity) {
        noteDao.deleteNote(note)
    }

}
