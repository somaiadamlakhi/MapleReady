package com.blueray.mapleready.data.practice

import com.blueray.mapleready.database.MapleReadyDatabase
import com.blueray.mapleready.domain.practice.PracticeRepository
import com.blueray.mapleready.domain.practice.model.AnswerOption
import com.blueray.mapleready.domain.practice.model.PracticeQuestion
import com.blueray.mapleready.domain.study.model.StudyTopicId

class PracticeRepositoryImpl(
    private val database: MapleReadyDatabase
) : PracticeRepository {

    private val queries = database.practiceQueries

    override fun getQuestions(): List<PracticeQuestion> {
        return queries
            .selectAllQuestions()
            .executeAsList()
            .map { question ->
                question.toDomain()
            }
    }

    override fun getQuestionsByTopic(
        topicId: StudyTopicId
    ): List<PracticeQuestion> {
        return queries
            .selectQuestionsByTopic(
                topicId = topicId.name
            )
            .executeAsList()
            .map { question ->
                question.toDomain()
            }
    }

    private fun com.blueray.mapleready.database.PracticeQuestion.toDomain():
            PracticeQuestion {

        val answers = queries
            .selectAnswersForQuestion(
                questionId = id
            )
            .executeAsList()
            .map { answer ->
                AnswerOption(
                    id = answer.id,
                    text = answer.answerText,
                    isCorrect = answer.isCorrect == 1L
                )
            }

        return PracticeQuestion(
            id = id,
            topicId = StudyTopicId.valueOf(topicId),
            text = questionText,
            answers = answers
        )
    }
}