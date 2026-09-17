package com.blueray.mapleready.data.practice.model

import kotlinx.serialization.Serializable

@Serializable
data class PracticeSeedData(
    val version: Int,
    val questions: List<PracticeSeedQuestion>
)

@Serializable
data class PracticeSeedQuestion(
    val id: Long,
    val topicId: String,
    val question: Map<String, String>,
    val answers: List<PracticeSeedAnswer>
)

@Serializable
data class PracticeSeedAnswer(
    val id: Long,
    val isCorrect: Boolean,
    val text: Map<String, String>
)