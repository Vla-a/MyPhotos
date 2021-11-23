package com.example.myphotos.database

import androidx.room.*
import com.example.myphotos.data.Note
import com.example.schoolorgonizer.notes.database.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE userPhoto LIKE :userPhoto ORDER BY id")
    fun getNoteList(userPhoto:String): List<NotePhotoEntity>

    @Query("SELECT * FROM notes")
    fun getsNoteList(): Flow<List<NotePhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: NotePhotoEntity)

    @Delete
    suspend fun deleteNote(note: NotePhotoEntity)
}