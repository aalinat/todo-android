package com.mglabs.twopagetodo.data.room

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {
    @TypeConverter fun localDateTimeToDatestamp(localDateTime: LocalDateTime): Long = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()

    @TypeConverter
    fun datestampToLocalDateTime(value: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC)
}