package com.mglabs.twopagetodo.shared

object Config {
    val DATE_PATTERN: String = "dd/MM/yyyy"
    val DATE_TIME_PATTERN: String = "yyyy-MM-dd HH:mm"
    val DATASOURCE = DataSource.Room
}

enum class DataSource {
    Static,
    Room
}