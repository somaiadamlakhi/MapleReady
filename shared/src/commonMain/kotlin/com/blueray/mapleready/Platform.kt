package com.blueray.mapleready

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform