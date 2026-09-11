package com.blueray.mapleready.data.practice

import com.blueray.mapleready.database.MapleReadyDatabase
import com.blueray.mapleready.domain.study.model.StudyTopicId

class PracticeDataSeeder(
    private val database: MapleReadyDatabase
) {

    private val queries = database.practiceQueries

    fun seedIfNeeded() {
        val questionCount = queries
            .countQuestions()
            .executeAsOne()

        if (questionCount > 0L) {
            return
        }

        seedHistoryQuestion()
    }

    private fun seedHistoryQuestion() {

        val questionId = 1001L

        queries.insertQuestion(
            id = questionId,
            topicId = StudyTopicId.HISTORY.name
        )

        queries.insertQuestionText(
            questionId = questionId,
            languageCode = "en",
            questionText = "Who was Canada's first Prime Minister?"
        )

        queries.insertQuestionText(
            questionId = questionId,
            languageCode = "fr",
            questionText = "Qui a été le premier premier ministre du Canada?"
        )

        insertAnswer(
            id = 10011L,
            questionId = questionId,
            isCorrect = true,
            english = "Sir John A. Macdonald",
            french = "Sir John A. Macdonald"
        )

        insertAnswer(
            id = 10012L,
            questionId = questionId,
            isCorrect = false,
            english = "Sir Wilfrid Laurier",
            french = "Sir Wilfrid Laurier"
        )

        insertAnswer(
            id = 10013L,
            questionId = questionId,
            isCorrect = false,
            english = "Pierre Trudeau",
            french = "Pierre Trudeau"
        )

        insertAnswer(
            id = 10014L,
            questionId = questionId,
            isCorrect = false,
            english = "Lester B. Pearson",
            french = "Lester B. Pearson"
        )
    }

    private fun insertAnswer(
        id: Long,
        questionId: Long,
        isCorrect: Boolean,
        english: String,
        french: String
    ) {

        queries.insertAnswer(
            id = id,
            questionId = questionId,
            isCorrect = if (isCorrect) 1L else 0L
        )

        queries.insertAnswerText(
            answerId = id,
            languageCode = "en",
            answerText = english
        )

        queries.insertAnswerText(
            answerId = id,
            languageCode = "fr",
            answerText = french
        )
    }
}