package com.blueray.mapleready.data.practice

import com.blueray.mapleready.domain.practice.PracticeRepository
import com.blueray.mapleready.domain.practice.model.AnswerOption
import com.blueray.mapleready.domain.practice.model.PracticeQuestion
import com.blueray.mapleready.domain.study.model.StudyTopicId

class PracticeRepositoryImpl : PracticeRepository {

    private val questions = listOf<PracticeQuestion>()

//        listOf(
//        PracticeQuestion(
//            id = "history_q1",
//            topicId = StudyTopicId.HISTORY,
//            answers = listOf(
//                AnswerOption(
//                    id = "history_q1_a",
//                    isCorrect = true,
//                    text = ""
//                ),
//                AnswerOption(
//                    id = "history_q1_b",
//                    isCorrect = false,
//                    text = ""
//                ),
//                AnswerOption(
//                    id = "history_q1_c",
//                    isCorrect = false,
//                    text = ""
//                ),
//                AnswerOption(
//                    id = "history_q1_d",
//                    isCorrect = false,
//                    text = ""
//                )
//            ),
//            text = ""
//        )
//    )

    override fun getQuestions(): List<PracticeQuestion> {
        return questions
    }

    override fun getQuestionsByTopic(
        topicId: StudyTopicId
    ): List<PracticeQuestion> {
        return questions.filter { question ->
            question.topicId == topicId
        }
    }
}