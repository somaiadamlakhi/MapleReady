package com.blueray.mapleready.presentation.practice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PracticeScreen(
    state: PracticeUiState,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Practice",
            style = MaterialTheme.typography.headlineMedium
        )

        val question = state.questions.firstOrNull()

        if (question == null) {

            Text(
                text = "No practice questions available."
            )

        } else {

            Text(
                text = question.text,
                style = MaterialTheme.typography.titleLarge
            )

            question.answers.forEach { answer ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors()
                ) {
                    Text(
                        text = answer.text,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}