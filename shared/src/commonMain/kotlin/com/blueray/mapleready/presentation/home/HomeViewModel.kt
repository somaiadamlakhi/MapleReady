package com.blueray.mapleready.presentation.home

import androidx.lifecycle.ViewModel
import com.blueray.mapleready.domain.study.StudyRepository
import com.blueray.mapleready.domain.study.calculateProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val repository: StudyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        val topics = repository.getTopics()

        val progresses = topics.map { topic ->
            topic.calculateProgress(
                completedSectionIndexes =
                    repository.getCompletedSectionIndexes(topic.id)
            )
        }

        val readiness = if (progresses.isEmpty()) {
            0f
        } else {
            progresses.average().toFloat()
        }

        _uiState.value = HomeUiState(
            readiness = readiness
        )
    }
}