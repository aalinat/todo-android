package com.mglabs.twopagetodo.shared

object Config {
    val DATE_PATTERN: String = "dd/MM/yyyy"
    val DATASOURCE = DataSource.Room
}

enum class DataSource {
    Static,
    Room
}