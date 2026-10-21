package com.blueray.mapleready.data.practice

import com.blueray.mapleready.data.practice.model.PracticeSeedData
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

        val jsonText = Res
            .readBytes("files/practice_questions.json")
            .decodeToString()

        val seedData = json.decodeFromString<PracticeSeedData>(
            jsonText
        )

        val installedVersion = queries
            .getMetadataValue(
                key = QUESTION_BANK_VERSION_KEY
            )
            .executeAsOneOrNull()
            ?.toIntOrNull()
            ?: 0

        if (installedVersion >= seedData.version) {
            return
        }

        validateSeedData(seedData)

        database.transaction {

            // Replace only the bundled practice content.
            // Other application tables remain untouched.

            queries.deleteAllAnswerTexts()
            queries.deleteAllAnswers()
            queries.deleteAllQuestionTexts()
            queries.deleteAllQuestions()

            seedData.questions.forEach { question ->

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

            queries.setMetadataValue(
                key = QUESTION_BANK_VERSION_KEY,
                value_ = seedData.version.toString()
            )
        }
    }

    private fun validateSeedData(
        seedData: PracticeSeedData
    ) {
        require(seedData.version > 0) {
            "Question bank version must be positive."
        }

        val questionIds = seedData.questions.map { it.id }

        require(questionIds.size == questionIds.toSet().size) {
            "Duplicate question IDs in practice JSON."
        }

        val answerIds = seedData.questions
            .flatMap { it.answers }
            .map { it.id }

        require(answerIds.size == answerIds.toSet().size) {
            "Duplicate answer IDs in practice JSON."
        }

        seedData.questions.forEach { question ->
            require(question.question["en"]?.isNotBlank() == true) {
                "Question ${question.id} has no English text."
            }

            require(question.answers.size >= 2) {
                "Question ${question.id} must have at least 2 answers."
            }

            require(question.answers.count { it.isCorrect } == 1) {
                "Question ${question.id} must have exactly one correct answer."
            }

            require(question.answers.all {
                it.text["en"]?.isNotBlank() == true
            }) {
                "Question ${question.id} has an answer without English text."
            }
        }
    }

    private companion object {
        const val QUESTION_BANK_VERSION_KEY =
            "question_bank_version"
    }
}