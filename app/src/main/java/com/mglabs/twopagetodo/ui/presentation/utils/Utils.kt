package com.mglabs.twopagetodo.ui.presentation.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun toFormattedDate(date: String): String {
    return LocalDateTime.parse(date).format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm"))
}