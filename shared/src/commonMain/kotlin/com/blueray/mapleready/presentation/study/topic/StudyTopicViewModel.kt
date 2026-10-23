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

    fun loadTopic() {
        repository.checkAndRefreshIfCompleted(topicId)

        val topic = repository.getTopicById(topicId)
        val savedIndex = repository.getLastVisitedSectionIndex(topicId)

        _uiState.value = topic?.toTopicUiState(
            currentSectionIndex = savedIndex
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
            val newIndex = currentIndex + 1
            repository.saveLastVisitedSectionIndex(topicId, newIndex)
            _uiState.value = state.copy(
                currentSectionIndex = newIndex
            )
        }
    }

    fun previousSection() {
        val currentState = _uiState.value ?: return

        if (currentState.hasPreviousSection) {
            val newIndex = currentState.currentSectionIndex - 1
            repository.saveLastVisitedSectionIndex(topicId, newIndex)
            _uiState.value = currentState.copy(
                currentSectionIndex = newIndex
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

    fun saveCurrentStateOnLeave() {
        val state = _uiState.value ?: return
        repository.saveLastVisitedSectionIndex(topicId, state.currentSectionIndex)
    }
}
