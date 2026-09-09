package com.blueray.mapleready.domain.study

import com.blueray.mapleready.domain.study.model.StudyTopic
import com.blueray.mapleready.domain.study.model.StudyTopicId

interface StudyRepository {

    fun getTopics(): List<StudyTopic>

    fun getTopicById(
        id: StudyTopicId
    ): StudyTopic?

    fun markSectionCompleted(
        topicId: StudyTopicId,
        sectionIndex: Int
    )

    fun getCompletedSectionIndexes(
        topicId: StudyTopicId
    ): Set<Int>
}