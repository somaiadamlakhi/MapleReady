package com.blueray.mapleready.domain.practice

import com.blueray.mapleready.domain.practice.model.PracticeQuestion
import com.blueray.mapleready.domain.study.model.StudyTopicId

interface PracticeRepository {

    fun getQuestions(): List<PracticeQuestion>

    fun getQuestionsByTopic(
        topicId: StudyTopicId
    ): List<PracticeQuestion>
}