package com.blueray.mapleready.presentation.practice

import androidx.lifecycle.ViewModel
import com.blueray.mapleready.domain.language.AppLanguage
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
        topicId: StudyTopicId? = null,
        language: AppLanguage = AppLanguage.ENGLISH,
        limit: Int = 20
    ) {
        val allQuestions = if (topicId != null) {
            repository.getQuestionsByTopic(
                topicId = topicId,
                language = language
            )
        } else {
            repository.getQuestions(
                language = language
            )
        }

        val selectedQuestions = allQuestions
            .shuffled()
            .take(limit)
            .map { question ->
                question.copy(
                    answers = question.answers.shuffled()
                )
            }

        _uiState.value = PracticeUiState(
            questions = selectedQuestions,
            currentQuestionIndex = 0,
            selectedAnswerId = null,
            isAnswerChecked = false
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
        val state = _uiState.value
        if (state.selectedAnswerId == null || state.isAnswerChecked) {
            return
        }

        val isCorrect = state.selectedAnswerIsCorrect

        _uiState.value = state.copy(
            isAnswerChecked = true,
            correctAnswersCount = if (isCorrect) state.correctAnswersCount + 1 else state.correctAnswersCount
        )
    }

    fun nextQuestion() {
        val state = _uiState.value

        if (state.isLastQuestion) {
            _uiState.value = state.copy(
                isCompleted = true
            )
            return
        }

        _uiState.value = state.copy(
            currentQuestionIndex = state.currentQuestionIndex + 1,
            selectedAnswerId = null,
            isAnswerChecked = false
        )
    }

    fun finishPractice() {
        _uiState.value = _uiState.value.copy(
            isCompleted = true
        )
    }
}