package com.blueray.mapleready.data.study

import com.blueray.mapleready.domain.model.StudySection
import com.blueray.mapleready.domain.model.StudyTopic
import com.blueray.mapleready.domain.model.StudyTopicId
import com.blueray.mapleready.domain.study.StudyRepository

class StudyRepositoryImpl : StudyRepository {

    private val topics = listOf(
        StudyTopic(
            id = StudyTopicId.HISTORY,
            progress = 0.65f,
            sections = listOf(
                StudySection(
                    titleKey = "topic_history_section_1",
                    contentKey = "topic_history_content_1"
                ),
                StudySection(
                    titleKey = "topic_history_section_2",
                    contentKey = "topic_history_content_2"
                )
            )
        ),
        StudyTopic(
            id = StudyTopicId.RIGHTS_AND_RESPONSIBILITIES,
            progress = 0.40f,
            sections = emptyList()
        ),
        StudyTopic(
            id = StudyTopicId.GOVERNMENT,
            progress = 0.80f,
            sections = emptyList()
        ),
        StudyTopic(
            id = StudyTopicId.ELECTIONS,
            progress = 0.25f,
            sections = emptyList()
        ),
        StudyTopic(
            id = StudyTopicId.GEOGRAPHY,
            progress = 0.50f,
            sections = emptyList()
        )
    )

    override fun getTopics(): List<StudyTopic> {
        return topics
    }

    override fun getTopicById(
        id: StudyTopicId
    ): StudyTopic? {
        return topics.firstOrNull { it.id == id }
    }
}