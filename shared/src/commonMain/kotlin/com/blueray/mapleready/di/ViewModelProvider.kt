package com.blueray.mapleready.di

import com.blueray.mapleready.domain.practice.PracticeRepository
import com.blueray.mapleready.domain.study.StudyRepository
import com.blueray.mapleready.domain.study.model.StudyTopicId
import com.blueray.mapleready.presentation.home.HomeViewModel
import com.blueray.mapleready.presentation.progress.ProgressViewModel
import com.blueray.mapleready.presentation.study.StudyViewModel
import com.blueray.mapleready.presentation.study.topic.StudyTopicViewModel

class ViewModelProvider(
    private val studyRepository: StudyRepository,
    private val practiceRepository: PracticeRepository
) {

    fun provideStudyViewModel(): StudyViewModel =
        StudyViewModel(
            repository = studyRepository
        )

    fun provideProgressViewModel(): ProgressViewModel =
        ProgressViewModel(
            repository = studyRepository
        )

    fun provideHomeViewModel(): HomeViewModel =
        HomeViewModel(
            repository = studyRepository
        )

    fun provideStudyTopicViewModel(
        topicId: StudyTopicId
    ): StudyTopicViewModel =
        StudyTopicViewModel(
            topicId = topicId,
            repository = studyRepository
        )
}