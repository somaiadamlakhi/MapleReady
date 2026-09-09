package com.blueray.mapleready.domain.study

import com.blueray.mapleready.domain.study.model.StudyTopic

fun StudyTopic.calculateProgress(
    completedSectionIndexes: Set<Int>
): Float {

    if (sections.isEmpty()) {
        return 0f
    }

    val completedCount = completedSectionIndexes.count { index ->
        index in sections.indices
    }

    return completedCount.toFloat() / sections.size
}