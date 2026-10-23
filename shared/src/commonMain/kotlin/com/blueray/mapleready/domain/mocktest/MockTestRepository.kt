package com.blueray.mapleready.domain.mocktest

import com.blueray.mapleready.domain.language.AppLanguage
import com.blueray.mapleready.domain.practice.model.PracticeQuestion

interface MockTestRepository {
    fun generateMockTestQuestions(
        language: AppLanguage = AppLanguage.ENGLISH,
        count: Int = 20
    ): List<PracticeQuestion>
}
