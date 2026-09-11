package com.blueray.mapleready

import androidx.compose.ui.window.ComposeUIViewController
import com.blueray.mapleready.database.IosDatabaseDriverFactory

fun MainViewController() = ComposeUIViewController {
    App(
        driverFactory = IosDatabaseDriverFactory()
    )
}