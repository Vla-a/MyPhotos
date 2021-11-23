package com.example.myphotos.database

import androidx.room.*
import com.example.schoolorgonizer.notes.database.PhotoEntity


@Entity(
    tableName = "notes", indices = [Index(value = ["title"], unique = true)],

)
class NotePhotoEntity(

    val id: Long = 0L,
@PrimaryKey val title: String,
    val date: String,
    val userPhoto: String


)