package com.blueray.mapleready.presentation.progress

import androidx.lifecycle.ViewModel
import com.blueray.mapleready.domain.study.StudyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProgressViewModel(
    private val repository: StudyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProgressUiState()
    )

    val uiState: StateFlow<ProgressUiState> =
        _uiState.asStateFlow()

    init {
        loadProgress()
    }

    private fun loadProgress() {
        _uiState.value = repository
            .getTopics()
            .toProgressUiState()
    }
}