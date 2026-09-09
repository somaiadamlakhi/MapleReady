package com.blueray.mapleready.domain.study.model

data class StudyTopic(
    val id: StudyTopicId,
    val sections: List<StudySection>
)