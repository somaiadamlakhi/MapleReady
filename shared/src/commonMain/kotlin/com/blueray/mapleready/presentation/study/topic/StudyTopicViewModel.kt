package com.blueray.mapleready.presentation.study.topic

import androidx.lifecycle.ViewModel
import com.blueray.mapleready.domain.study.model.StudyTopicId
import com.blueray.mapleready.domain.study.StudyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StudyTopicViewModel(
    private val topicId: StudyTopicId,
    private val repository: StudyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<StudyTopicUiState?>(null)

    val uiState: StateFlow<StudyTopicUiState?> =
        _uiState.asStateFlow()

    init {
        loadTopic()
    }

    private fun loadTopic() {
        val topic = repository.getTopicById(topicId)

        _uiState.value = topic?.toTopicUiState(
            currentSectionIndex = 0
        )
    }

    fun nextSection() {
        val state = _uiState.value ?: return

        val currentIndex = state.currentSectionIndex

        repository.markSectionCompleted(
            topicId = topicId,
            sectionIndex = currentIndex
        )

        if (currentIndex < state.sections.lastIndex) {
            _uiState.value = state.copy(
                currentSectionIndex = currentIndex + 1
            )
        }
    }

    fun previousSection() {
        val currentState = _uiState.value ?: return

        if (currentState.hasPreviousSection) {
            _uiState.value = currentState.copy(
                currentSectionIndex =
                    currentState.currentSectionIndex - 1
            )
        }
    }


    fun markCurrentSectionCompleted() {
        val state = _uiState.value ?: return

        repository.markSectionCompleted(
            topicId = topicId,
            sectionIndex = state.currentSectionIndex
        )
    }
}