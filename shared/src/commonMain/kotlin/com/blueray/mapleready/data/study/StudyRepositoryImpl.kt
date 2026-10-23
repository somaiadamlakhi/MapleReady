package com.blueray.mapleready.data.study

import com.blueray.mapleready.domain.study.model.StudySection
import com.blueray.mapleready.domain.study.model.StudyTopic
import com.blueray.mapleready.domain.study.model.StudyTopicId
import com.blueray.mapleready.domain.study.StudyRepository

class StudyRepositoryImpl : StudyRepository {
    private val completedSections =
        mutableMapOf<StudyTopicId, MutableSet<Int>>()

    private val lastVisitedSectionIndexes =
        mutableMapOf<StudyTopicId, Int>()

    private val activeBatches =
        mutableMapOf<StudyTopicId, Int>()

    override fun getTopics(): List<StudyTopic> {
        return StudyTopicId.entries.map { getTopicById(it) }
    }

    override fun getTopicById(
        id: StudyTopicId
    ): StudyTopic {
        val batch = activeBatches[id] ?: 0
        val range = if (batch == 0) (1..10) else (11..20)

        val prefix = when (id) {
            StudyTopicId.HISTORY -> "topic_history"
            StudyTopicId.RIGHTS_AND_RESPONSIBILITIES -> "topic_rights"
            StudyTopicId.GOVERNMENT -> "topic_government"
            StudyTopicId.ELECTIONS -> "topic_elections"
            StudyTopicId.GEOGRAPHY -> "topic_geography"
        }

        val sections = range.map { i ->
            StudySection(
                titleKey = "${prefix}_section_$i",
                contentKey = "${prefix}_content_$i"
            )
        }

        return StudyTopic(
            id = id,
            sections = sections
        )
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

    override fun getLastVisitedSectionIndex(
        topicId: StudyTopicId
    ): Int {
        return lastVisitedSectionIndexes[topicId] ?: 0
    }

    override fun saveLastVisitedSectionIndex(
        topicId: StudyTopicId,
        sectionIndex: Int
    ) {
        lastVisitedSectionIndexes[topicId] = sectionIndex
    }

    override fun checkAndRefreshIfCompleted(
        topicId: StudyTopicId
    ) {
        val completed = getCompletedSectionIndexes(topicId)
        val topic = getTopicById(topicId)

        if (completed.size >= topic.sections.size && topic.sections.isNotEmpty()) {
            completedSections[topicId]?.clear()
            val currentBatch = activeBatches[topicId] ?: 0
            activeBatches[topicId] = (currentBatch + 1) % 2
            lastVisitedSectionIndexes[topicId] = 0
        }
    }
}
