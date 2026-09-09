package com.blueray.mapleready.di

import com.blueray.mapleready.data.study.StudyRepositoryImpl
import com.blueray.mapleready.domain.study.StudyRepository

class AppContainer {

    val studyRepository: StudyRepository by lazy {
        StudyRepositoryImpl()
    }

    val viewModelProvider: ViewModelProvider by lazy {
        ViewModelProvider(
            studyRepository = studyRepository
        )
    }
}