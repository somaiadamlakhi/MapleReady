package com.blueray.mapleready.data.practice

import com.blueray.mapleready.data.practice.model.PracticeSeedQuestion
import com.blueray.mapleready.database.MapleReadyDatabase
import kotlinx.serialization.json.Json
import mapleready.shared.generated.resources.Res

class PracticeDataSeeder(
    private val database: MapleReadyDatabase
) {

    private val queries = database.practiceQueries

    private val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun seedIfNeeded() {

        val questionCount = queries
            .countQuestions()
            .executeAsOne()

        if (questionCount > 0L) {
            return
        }

        val jsonText = Res
            .readBytes("files/practice_questions.json")
            .decodeToString()

        val questions =
            json.decodeFromString<List<PracticeSeedQuestion>>(
                jsonText
            )

        questions.forEach { question ->
            insertQuestion(question)
        }
    }

    private fun insertQuestion(
        question: PracticeSeedQuestion
    ) {

        queries.insertQuestion(
            id = question.id,
            topicId = question.topicId
        )

        question.question.forEach { (languageCode, text) ->

            queries.insertQuestionText(
                questionId = question.id,
                languageCode = languageCode,
                questionText = text
            )
        }

        question.answers.forEach { answer ->

            queries.insertAnswer(
                id = answer.id,
                questionId = question.id,
                isCorrect = if (answer.isCorrect) 1L else 0L
            )

            answer.text.forEach { (languageCode, text) ->

                queries.insertAnswerText(
                    answerId = answer.id,
                    languageCode = languageCode,
                    answerText = text
                )
            }
        }
    }
}