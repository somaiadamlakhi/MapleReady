package com.blueray.mapleready.data.practice

import com.blueray.mapleready.database.MapleReadyDatabase
import com.blueray.mapleready.domain.language.AppLanguage
import com.blueray.mapleready.domain.practice.PracticeRepository
import com.blueray.mapleready.domain.practice.model.AnswerOption
import com.blueray.mapleready.domain.practice.model.PracticeQuestion
import com.blueray.mapleready.domain.study.model.StudyTopicId

class PracticeRepositoryImpl(
    private val database: MapleReadyDatabase
) : PracticeRepository {

    private val queries = database.practiceQueries

    override fun getQuestions(
        language: AppLanguage
    ): List<PracticeQuestion> {

        return queries
            .selectAllQuestionsByLanguage(
                languageCode = language.code
            )
            .executeAsList()
            .map { question ->

                PracticeQuestion(
                    id = question.id,
                    topicId = StudyTopicId.valueOf(
                        question.topicId
                    ),
                    text = question.questionText,
                    answers = getAnswers(
                        questionId = question.id,
                        language = language
                    )
                )
            }
    }

    override fun getQuestionsByTopic(
        topicId: StudyTopicId,
        language: AppLanguage
    ): List<PracticeQuestion> {

        return queries
            .selectQuestionsByTopicAndLanguage(
                topicId = topicId.name,
                languageCode = language.code
            )
            .executeAsList()
            .map { question ->

                PracticeQuestion(
                    id = question.id,
                    topicId = StudyTopicId.valueOf(
                        question.topicId
                    ),
                    text = question.questionText,
                    answers = getAnswers(
                        questionId = question.id,
                        language = language
                    )
                )
            }
    }

    private fun getAnswers(
        questionId: Long,
        language: AppLanguage
    ): List<AnswerOption> {

        return queries
            .selectAnswersForQuestionAndLanguage(
                questionId = questionId,
                languageCode = language.code
            )
            .executeAsList()
            .map { answer ->

                AnswerOption(
                    id = answer.id,
                    text = answer.answerText,
                    isCorrect = answer.isCorrect == 1L
                )
            }
    }
}