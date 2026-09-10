package com.blueray.mapleready.domain.practice.model

import com.blueray.mapleready.domain.study.model.StudyTopicId
data class PracticeQuestion(
    val id: Long,
    val topicId: StudyTopicId,
    val text: String,
    val answers: List<AnswerOption>
)