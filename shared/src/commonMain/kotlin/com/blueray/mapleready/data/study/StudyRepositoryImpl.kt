package com.blueray.mapleready.data.study

import com.blueray.mapleready.domain.model.StudyTopic
import com.blueray.mapleready.domain.model.StudyTopicId
import com.blueray.mapleready.domain.study.StudyRepository

class StudyRepositoryImpl : StudyRepository {

    private val topics = listOf(
        StudyTopic(
            id = StudyTopicId.HISTORY,
            progress = 0.65f
        ),
        StudyTopic(
            id = StudyTopicId.RIGHTS_AND_RESPONSIBILITIES,
            progress = 0.40f
        ),
        StudyTopic(
            id = StudyTopicId.GOVERNMENT,
            progress = 0.80f
        ),
        StudyTopic(
            id = StudyTopicId.ELECTIONS,
            progress = 0.25f
        ),
        StudyTopic(
            id = StudyTopicId.GEOGRAPHY,
            progress = 0.50f
        )
    )

    override fun getTopics(): List<StudyTopic> {
        return topics
    }

    override fun getTopicById(
        id: StudyTopicId
    ): StudyTopic? {
        return topics.firstOrNull {
            it.id == id
        }
    }
}