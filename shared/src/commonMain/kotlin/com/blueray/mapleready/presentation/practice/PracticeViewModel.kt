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

    fun selectAnswer(
        answerId: Long
    ) {
        if (_uiState.value.isAnswerChecked) {
            return
        }

        _uiState.value = _uiState.value.copy(
            selectedAnswerId = answerId
        )
    }

    fun checkAnswer() {
        if (_uiState.value.selectedAnswerId == null) {
            return
        }

        _uiState.value = _uiState.value.copy(
            isAnswerChecked = true
        )
    }

    fun nextQuestion() {
        val state = _uiState.value

        if (state.isLastQuestion) {
            return
        }

        _uiState.value = state.copy(
            currentQuestionIndex = state.currentQuestionIndex + 1,
            selectedAnswerId = null,
            isAnswerChecked = false
        )
    }
}