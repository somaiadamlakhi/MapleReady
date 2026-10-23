package com.blueray.mapleready.presentation.mocktest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blueray.mapleready.domain.language.AppLanguage
import com.blueray.mapleready.domain.mocktest.MockTestRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MockTestViewModel(
    private val repository: MockTestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MockTestUiState())
    val uiState: StateFlow<MockTestUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun startTest(language: AppLanguage = AppLanguage.ENGLISH) {
        timerJob?.cancel()

        val questions = repository.generateMockTestQuestions(
            language = language,
            count = 20
        )

        _uiState.value = MockTestUiState(
            stage = MockTestStage.IN_PROGRESS,
            questions = questions,
            currentQuestionIndex = 0,
            userAnswers = emptyMap(),
            flaggedQuestionIds = emptySet(),
            remainingTimeSeconds = 45 * 60L,
            showSubmitConfirmation = false,
            timeSpentSeconds = 0L
        )

        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_uiState.value.remainingTimeSeconds > 0 && _uiState.value.stage == MockTestStage.IN_PROGRESS) {
                delay(1000L)
                _uiState.update { state ->
                    val newRemaining = state.remainingTimeSeconds - 1
                    val newSpent = state.timeSpentSeconds + 1
                    if (newRemaining <= 0) {
                        state.copy(
                            remainingTimeSeconds = 0,
                            timeSpentSeconds = newSpent,
                            stage = MockTestStage.COMPLETED,
                            showSubmitConfirmation = false
                        )
                    } else {
                        state.copy(
                            remainingTimeSeconds = newRemaining,
                            timeSpentSeconds = newSpent
                        )
                    }
                }
            }
        }
    }

    fun selectAnswer(answerId: Long) {
        val q = _uiState.value.currentQuestion ?: return
        _uiState.update { state ->
            val updated = state.userAnswers.toMutableMap()
            updated[q.id] = answerId
            state.copy(userAnswers = updated)
        }
    }

    fun toggleFlagCurrentQuestion() {
        val q = _uiState.value.currentQuestion ?: return
        _uiState.update { state ->
            val updatedFlags = state.flaggedQuestionIds.toMutableSet()
            if (updatedFlags.contains(q.id)) {
                updatedFlags.remove(q.id)
            } else {
                updatedFlags.add(q.id)
            }
            state.copy(flaggedQuestionIds = updatedFlags)
        }
    }

    fun goToQuestion(index: Int) {
        val count = _uiState.value.totalQuestions
        if (index in 0 until count) {
            _uiState.update { it.copy(currentQuestionIndex = index) }
        }
    }

    fun nextQuestion() {
        val state = _uiState.value
        if (state.currentQuestionIndex < state.totalQuestions - 1) {
            _uiState.update { it.copy(currentQuestionIndex = it.currentQuestionIndex + 1) }
        }
    }

    fun previousQuestion() {
        val state = _uiState.value
        if (state.currentQuestionIndex > 0) {
            _uiState.update { it.copy(currentQuestionIndex = it.currentQuestionIndex - 1) }
        }
    }

    fun requestSubmitTest() {
        _uiState.update { it.copy(showSubmitConfirmation = true) }
    }

    fun dismissSubmitConfirmation() {
        _uiState.update { it.copy(showSubmitConfirmation = false) }
    }

    fun confirmSubmitTest() {
        timerJob?.cancel()
        _uiState.update { state ->
            state.copy(
                stage = MockTestStage.COMPLETED,
                showSubmitConfirmation = false
            )
        }
    }

    fun resetTest() {
        timerJob?.cancel()
        _uiState.value = MockTestUiState()
    }
}
