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
import com.blueray.mapleready.navigation.MapleReadyNavigation
import com.blueray.mapleready.presentation.theme.MapleReadyTheme

@Composable
fun App(
    driverFactory: DatabaseDriverFactory
) {

    val appContainer = remember {
        AppContainer(
            driverFactory = driverFactory
        )
    }

    var isInitialized by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(appContainer) {
        appContainer.initialize()
        isInitialized = true
    }

    MapleReadyTheme {

        if (isInitialized) {

            MapleReadyNavigation(
                viewModelProvider = appContainer.viewModelProvider
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