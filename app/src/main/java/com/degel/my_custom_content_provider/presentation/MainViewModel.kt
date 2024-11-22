package com.degel.my_custom_content_provider.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.degel.my_custom_content_provider.domain.model.Note
import com.degel.my_custom_content_provider.domain.use_cases.DeleteUseCase
import com.degel.my_custom_content_provider.domain.use_cases.GetAllNotesUseCase
import com.degel.my_custom_content_provider.domain.use_cases.InsertUseCase
import com.degel.my_custom_content_provider.domain.use_cases.UpdateUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val insertUseCase: InsertUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val updateUseCase: UpdateUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {

    val uiState =
        getAllNotesUseCase.invoke()
            .map { UiState(it) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, UiState())


    fun insert(note: Note) = viewModelScope.launch {
        insertUseCase.invoke(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        updateUseCase.invoke(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        deleteUseCase.invoke(note)
    }

}

data class UiState(
    val data: List<Note> = emptyList()
)
