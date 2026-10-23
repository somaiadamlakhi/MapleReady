package com.blueray.mapleready.di

import com.blueray.mapleready.domain.mocktest.MockTestRepository
import com.blueray.mapleready.domain.practice.PracticeRepository
import com.blueray.mapleready.domain.study.StudyRepository
import com.blueray.mapleready.domain.study.model.StudyTopicId
import com.blueray.mapleready.presentation.home.HomeViewModel
import com.blueray.mapleready.presentation.mocktest.MockTestViewModel
import com.blueray.mapleready.presentation.practice.PracticeViewModel
import com.blueray.mapleready.presentation.progress.ProgressViewModel
import com.blueray.mapleready.presentation.study.StudyViewModel
import com.blueray.mapleready.presentation.study.topic.StudyTopicViewModel

class ViewModelProvider(
    private val studyRepository: StudyRepository,
    private val practiceRepository: PracticeRepository,
    private val mockTestRepository: MockTestRepository
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

    fun providePracticeViewModel(): PracticeViewModel =
        PracticeViewModel(
            repository = practiceRepository
        )

    fun provideMockTestViewModel(): MockTestViewModel =
        MockTestViewModel(
            repository = mockTestRepository
        )
}
