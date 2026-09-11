package com.blueray.mapleready.presentation.practice

import androidx.lifecycle.ViewModel
import com.blueray.mapleready.domain.practice.PracticeRepository
import com.blueray.mapleready.domain.study.model.StudyTopicId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PracticeViewModel(
    private val repository: PracticeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        PracticeUiState()
    )

    val uiState: StateFlow<PracticeUiState> =
        _uiState.asStateFlow()

    fun loadQuestions(
        topicId: StudyTopicId
    ) {
        val questions = repository.getQuestionsByTopic(
            topicId = topicId
        )

        _uiState.value = PracticeUiState(
            questions = questions
        )
    }
}