package com.blueray.mapleready.database

import app.cash.sqldelight.db.SqlDriver

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(
    driverFactory: DatabaseDriverFactory
): MapleReadyDatabase {
    val driver = driverFactory.createDriver()

    return MapleReadyDatabase(
        driver = driver
    )
}