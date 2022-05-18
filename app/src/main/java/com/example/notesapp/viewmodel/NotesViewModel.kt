package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.AppRepository
import com.example.notesapp.model.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: AppRepository) : ViewModel() {

    fun insert(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.upsert(note)
    }

    fun delete(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun update(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun getAll() = repository.getAll()

}