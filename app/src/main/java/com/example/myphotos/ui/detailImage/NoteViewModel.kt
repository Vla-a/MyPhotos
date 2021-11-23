package com.example.myphotos.ui.detailImage

import androidx.lifecycle.*
import com.example.myphotos.data.Note
import com.example.myphotos.data.Photo
import com.example.myphotos.database.NotePhotoEntity
import com.example.myphotos.repositorues.NoteRepository
import com.example.myphotos.repositorues.PhotoRepository
import com.example.schoolorgonizer.notes.database.PhotoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel(
    val noteRepository: NoteRepository
): ViewModel() {

    val noteListLiveData: LiveData<List<Note>> =
        noteRepository.getNoteList().asLiveData()

    fun addPhotoToDatabase(note: Note) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                noteRepository.addNote(note.entity())
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note.entity())
        }
    }

    private fun Note.entity() = NotePhotoEntity( 0, this.note, this.date, this.userPhoto)
}