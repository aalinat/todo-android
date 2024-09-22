package com.mglabs.twopagetodo.ui.presentation.utils

import com.mglabs.twopagetodo.shared.Config
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun toFormattedDate(date: String): String {
    return LocalDateTime.parse(date).format(DateTimeFormatter.ofPattern(Config.DATE_TIME_PATTERN))
}

fun validateText(text: String?): Boolean {
    return !text.isNullOrEmpty()
}

fun getTodaysDate(): String {
    return LocalDate.now().format(DateTimeFormatter.ofPattern(Config.DATE_PATTERN))
}