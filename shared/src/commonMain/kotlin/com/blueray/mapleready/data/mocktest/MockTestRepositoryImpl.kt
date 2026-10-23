package com.blueray.mapleready.data.mocktest

import com.blueray.mapleready.domain.language.AppLanguage
import com.blueray.mapleready.domain.mocktest.MockTestRepository
import com.blueray.mapleready.domain.practice.PracticeRepository
import com.blueray.mapleready.domain.practice.model.PracticeQuestion

class MockTestRepositoryImpl(
    private val practiceRepository: PracticeRepository
) : MockTestRepository {

    override fun generateMockTestQuestions(
        language: AppLanguage,
        count: Int
    ): List<PracticeQuestion> {
        val allQuestions = practiceRepository.getQuestions(language = language)
        return allQuestions
            .shuffled()
            .take(count)
            .map { question ->
                question.copy(
                    answers = question.answers.shuffled()
                )
            }
    }
}
