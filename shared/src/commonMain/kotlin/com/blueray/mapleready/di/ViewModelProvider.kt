package com.blueray.mapleready.di

import com.blueray.mapleready.domain.model.StudyTopicId
import com.blueray.mapleready.domain.study.StudyRepository
import com.blueray.mapleready.presentation.progress.ProgressViewModel
import com.blueray.mapleready.presentation.study.StudyViewModel
import com.blueray.mapleready.presentation.study.topic.StudyTopicViewModel

class ViewModelProvider(
    private val studyRepository: StudyRepository
) {

    fun provideStudyViewModel(): StudyViewModel {
        return StudyViewModel(
            repository = studyRepository
        )
    }

    fun provideProgressViewModel(): ProgressViewModel {
        return ProgressViewModel(
            repository = studyRepository
        )
    }

    fun provideStudyTopicViewModel(
        topicId: StudyTopicId
    ): StudyTopicViewModel {
        return StudyTopicViewModel(
            topicId = topicId,
            repository = studyRepository
        )
    }
}