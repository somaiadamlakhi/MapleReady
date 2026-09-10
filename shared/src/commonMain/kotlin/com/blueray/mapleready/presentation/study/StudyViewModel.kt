package com.blueray.mapleready.presentation.study

import androidx.lifecycle.ViewModel
import com.blueray.mapleready.domain.study.StudyRepository
import com.blueray.mapleready.domain.study.calculateProgress
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
        refresh()
    }

    fun refresh() {
        val topics = repository
            .getTopics()
            .map { topic ->

                val completedSections =
                    repository.getCompletedSectionIndexes(
                        topicId = topic.id
                    )

                val progress = topic.calculateProgress(
                    completedSectionIndexes = completedSections
                )

                topic.toUiModel(
                    progress = progress
                )
            }

        _uiState.value = StudyUiState(
            topics = topics
        )
    }
}