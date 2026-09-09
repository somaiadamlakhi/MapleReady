package com.blueray.mapleready.presentation.study

import androidx.lifecycle.ViewModel
import com.blueray.mapleready.domain.study.StudyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StudyViewModel(
    private val repository: StudyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        StudyUiState()
    )

    val uiState: StateFlow<StudyUiState> =
        _uiState.asStateFlow()

    init {
        loadTopics()
    }

    private fun loadTopics() {
        val topics = repository
            .getTopics()
            .map { topic ->
                topic.toUiModel()
            }

        _uiState.value = StudyUiState(
            topics = topics
        )
    }
}