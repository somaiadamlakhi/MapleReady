package com.blueray.mapleready

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.blueray.mapleready.database.DatabaseDriverFactory
import com.blueray.mapleready.di.AppContainer
import com.blueray.mapleready.navigation.MapleReadyNavigation
import com.blueray.mapleready.presentation.theme.MapleReadyTheme

@Composable
fun App(
    driverFactory: DatabaseDriverFactory
) {
    MapleReadyTheme {

        val appContainer = remember {
            AppContainer(
                driverFactory = driverFactory
            )
        }

        MapleReadyNavigation(
            viewModelProvider = appContainer.viewModelProvider
        )
    }
}