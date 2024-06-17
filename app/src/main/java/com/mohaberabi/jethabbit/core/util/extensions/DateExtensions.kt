package com.mohaberabi.jethabbit.core.util.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun ZonedDateTime.toStartOfDateTimestamp(): Long {
    return truncatedTo(ChronoUnit.DAYS).toEpochSecond() * 1000
}

fun LocalTime.toZonedDateTime(): ZonedDateTime =
    atDate(LocalDate.now()).atZone(ZoneId.systemDefault())

fun LocalDate.toZonedDateTime(): ZonedDateTime = atStartOfDay(ZoneId.systemDefault())

fun ZonedDateTime.toTimeStamp(): Long = toInstant().toEpochMilli()

fun Long.toZonedDateTime(): ZonedDateTime {
    return ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
}