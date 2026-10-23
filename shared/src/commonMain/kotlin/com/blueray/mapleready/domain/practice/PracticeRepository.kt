package com.blueray.mapleready.domain.practice

import com.blueray.mapleready.domain.language.AppLanguage
import com.blueray.mapleready.domain.practice.model.PracticeQuestion
import com.blueray.mapleready.domain.study.model.StudyTopicId

interface PracticeRepository {

    fun getQuestions(
        language: AppLanguage = AppLanguage.ENGLISH
    ): List<PracticeQuestion>

    fun getQuestionsByTopic(
        topicId: StudyTopicId,
        language: AppLanguage = AppLanguage.ENGLISH
    ): List<PracticeQuestion>

    fun getSavedLanguage(): AppLanguage

    fun saveLanguage(language: AppLanguage)
}
