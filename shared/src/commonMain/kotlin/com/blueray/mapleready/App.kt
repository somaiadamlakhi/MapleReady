package com.blueray.mapleready

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.blueray.mapleready.di.AppContainer
import com.blueray.mapleready.navigation.MapleReadyNavigation
import com.blueray.mapleready.presentation.theme.MapleReadyTheme

@Composable
fun App() {
    MapleReadyTheme {

        val appContainer = remember {
            AppContainer()
        }

        MapleReadyNavigation(
            viewModelProvider = appContainer.viewModelProvider
        )
    }
}