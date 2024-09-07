package com.mglabs.twopagetodo.domain

import java.time.LocalDateTime

data class TodoTask(
    var id: Int,
    var author: String,
    var title: String,
    var content: String,
    var isFavorite: Boolean = false,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false
)