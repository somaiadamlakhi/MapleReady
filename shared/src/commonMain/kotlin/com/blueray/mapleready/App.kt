package com.blueray.mapleready

import androidx.compose.runtime.Composable
import com.blueray.mapleready.navigation.MapleReadyNavigation
import com.blueray.mapleready.presentation.theme.MapleReadyTheme

@Composable
fun App() {
    MapleReadyTheme {
        MapleReadyNavigation()
    }
}