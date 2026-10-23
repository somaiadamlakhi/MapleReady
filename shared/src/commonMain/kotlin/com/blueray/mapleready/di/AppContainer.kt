package com.blueray.mapleready.di

import com.blueray.mapleready.data.mocktest.MockTestRepositoryImpl
import com.blueray.mapleready.data.practice.PracticeDataSeeder
import com.blueray.mapleready.data.practice.PracticeRepositoryImpl
import com.blueray.mapleready.data.study.StudyRepositoryImpl
import com.blueray.mapleready.database.DatabaseDriverFactory
import com.blueray.mapleready.database.createDatabase
import com.blueray.mapleready.domain.mocktest.MockTestRepository
import com.blueray.mapleready.domain.practice.PracticeRepository
import com.blueray.mapleready.domain.study.StudyRepository

class AppContainer(
    driverFactory: DatabaseDriverFactory
) {

    private val database =
        createDatabase(driverFactory)

    private val practiceDataSeeder =
        PracticeDataSeeder(
            database = database
        )

    private val studyRepository: StudyRepository =
        StudyRepositoryImpl()

    private val practiceRepository: PracticeRepository =
        PracticeRepositoryImpl(
            database = database
        )

    private val mockTestRepository: MockTestRepository =
        MockTestRepositoryImpl(
            practiceRepository = practiceRepository
        )

    val viewModelProvider = ViewModelProvider(
        studyRepository = studyRepository,
        practiceRepository = practiceRepository,
        mockTestRepository = mockTestRepository
    )

    suspend fun initialize() {
        practiceDataSeeder.seedIfNeeded()
    }
}
