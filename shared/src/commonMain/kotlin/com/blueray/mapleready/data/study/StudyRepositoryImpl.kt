package com.blueray.mapleready.data.study

import com.blueray.mapleready.domain.study.model.StudySection
import com.blueray.mapleready.domain.study.model.StudyTopic
import com.blueray.mapleready.domain.study.model.StudyTopicId
import com.blueray.mapleready.domain.study.StudyRepository

class StudyRepositoryImpl : StudyRepository {
    private val completedSections =
        mutableMapOf<StudyTopicId, MutableSet<Int>>()
    private val topics = listOf(
        StudyTopic(
            id = StudyTopicId.HISTORY,
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
            sections = emptyList()
        ),
        StudyTopic(
            id = StudyTopicId.GOVERNMENT,
            sections = emptyList()
        ),
        StudyTopic(
            id = StudyTopicId.ELECTIONS,
            sections = emptyList()
        ),
        StudyTopic(
            id = StudyTopicId.GEOGRAPHY,
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

    override fun markSectionCompleted(
        topicId: StudyTopicId,
        sectionIndex: Int
    ) {
        completedSections
            .getOrPut(topicId) { mutableSetOf() }
            .add(sectionIndex)
    }

    override fun getCompletedSectionIndexes(
        topicId: StudyTopicId
    ): Set<Int> {
        return completedSections[topicId]
            ?.toSet()
            ?: emptySet()
    }

}