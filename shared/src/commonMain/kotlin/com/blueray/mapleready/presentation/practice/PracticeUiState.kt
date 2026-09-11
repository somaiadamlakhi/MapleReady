package com.blueray.mapleready.presentation.practice

import com.blueray.mapleready.domain.practice.model.PracticeQuestion

data class PracticeUiState(
    val questions: List<PracticeQuestion> = emptyList()
)