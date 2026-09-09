package com.blueray.mapleready.data.practice

import com.blueray.mapleready.domain.practice.PracticeRepository
import com.blueray.mapleready.domain.practice.model.PracticeQuestion
import com.blueray.mapleready.domain.study.model.StudyTopicId

class PracticeRepositoryImpl : PracticeRepository {

    private val questions = emptyList<PracticeQuestion>()

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