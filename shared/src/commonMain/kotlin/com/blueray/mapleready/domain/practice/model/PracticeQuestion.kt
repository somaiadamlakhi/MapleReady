package com.blueray.mapleready.domain.practice.model

import com.blueray.mapleready.domain.study.model.StudyTopicId

data class PracticeQuestion(
    val id: String,
    val topicId: StudyTopicId,
    val answers: List<AnswerOption>
)