package com.degel.my_custom_content_provider.domain.use_cases

import com.degel.my_custom_content_provider.domain.model.Note
import com.degel.my_custom_content_provider.domain.repository.NotesRepository
import javax.inject.Inject

class UpdateUseCase @Inject constructor(private val notesRepository: NotesRepository) {

    suspend operator fun invoke(note: Note) = notesRepository.update(note)

}