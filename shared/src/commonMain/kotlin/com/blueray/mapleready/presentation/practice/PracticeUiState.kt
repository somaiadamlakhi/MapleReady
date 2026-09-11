package com.blueray.mapleready.presentation.practice

import com.blueray.mapleready.domain.practice.model.PracticeQuestion

data class PracticeUiState(
    val questions: List<PracticeQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswerId: Long? = null,
    val isAnswerChecked: Boolean = false
) {

    val currentQuestion: PracticeQuestion?
        get() = questions.getOrNull(currentQuestionIndex)

    val totalQuestions: Int
        get() = questions.size

    val currentQuestionNumber: Int
        get() = if (questions.isEmpty()) 0 else currentQuestionIndex + 1

    val isLastQuestion: Boolean
        get() = currentQuestionIndex == questions.lastIndex

    val selectedAnswerIsCorrect: Boolean
        get() {
            val question = currentQuestion ?: return false

            return question.answers
                .firstOrNull { it.id == selectedAnswerId }
                ?.isCorrect == true
        }
}