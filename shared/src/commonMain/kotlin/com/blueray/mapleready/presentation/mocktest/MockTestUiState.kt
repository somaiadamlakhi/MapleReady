package com.blueray.mapleready.presentation.mocktest

import com.blueray.mapleready.domain.practice.model.PracticeQuestion

enum class MockTestStage {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED
}

data class MockTestUiState(
    val stage: MockTestStage = MockTestStage.NOT_STARTED,
    val questions: List<PracticeQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val userAnswers: Map<Long, Long> = emptyMap(),
    val flaggedQuestionIds: Set<Long> = emptySet(),
    val remainingTimeSeconds: Long = 45 * 60L,
    val showSubmitConfirmation: Boolean = false,
    val timeSpentSeconds: Long = 0L
) {
    val totalQuestions: Int
        get() = questions.size

    val currentQuestion: PracticeQuestion?
        get() = questions.getOrNull(currentQuestionIndex)

    val currentQuestionNumber: Int
        get() = if (questions.isEmpty()) 0 else currentQuestionIndex + 1

    val answeredQuestionsCount: Int
        get() = userAnswers.size

    val isCurrentQuestionFlagged: Boolean
        get() {
            val q = currentQuestion ?: return false
            return flaggedQuestionIds.contains(q.id)
        }

    val selectedAnswerIdForCurrentQuestion: Long?
        get() {
            val q = currentQuestion ?: return null
            return userAnswers[q.id]
        }

    val correctAnswersCount: Int
        get() {
            return questions.count { question ->
                val selectedId = userAnswers[question.id]
                val correctAnswer = question.answers.firstOrNull { it.isCorrect }
                correctAnswer != null && selectedId == correctAnswer.id
            }
        }

    val scorePercentage: Int
        get() = if (totalQuestions == 0) 0 else ((correctAnswersCount.toFloat() / totalQuestions) * 100).toInt()

    val isPassed: Boolean
        get() = correctAnswersCount >= 15
}
