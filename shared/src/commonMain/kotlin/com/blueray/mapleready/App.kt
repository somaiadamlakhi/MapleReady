package com.blueray.mapleready

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.blueray.mapleready.database.DatabaseDriverFactory
import com.blueray.mapleready.di.AppContainer
import com.blueray.mapleready.domain.language.AppLanguage
import com.blueray.mapleready.navigation.MapleReadyNavigation
import com.blueray.mapleready.presentation.theme.MapleReadyTheme

@Composable
fun App(
    driverFactory: DatabaseDriverFactory,
    onRestartApp: () -> Unit = {}
) {

    val appContainer = remember {
        AppContainer(
            driverFactory = driverFactory
        )
    }

    var isInitialized by remember {
        mutableStateOf(false)
    }

    var currentLanguage by remember {
        mutableStateOf(AppLanguage.ENGLISH)
    }

    LaunchedEffect(appContainer) {
        appContainer.initialize()
        val savedLang = appContainer.getSavedLanguage()
        currentLanguage = savedLang
        isInitialized = true
    }

    MapleReadyTheme {

        if (isInitialized) {

            MapleReadyNavigation(
                viewModelProvider = appContainer.viewModelProvider,
                currentLanguage = currentLanguage,
                onLanguageChangeRequested = { targetLanguage ->
                    appContainer.saveLanguage(targetLanguage)
                    onRestartApp()
                }
            )

        } else {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
