package com.mglabs.twopagetodo.shared

object Config {
    val DATASOURCE = DataSource.Room
}

enum class DataSource {
    Static,
    Room
}