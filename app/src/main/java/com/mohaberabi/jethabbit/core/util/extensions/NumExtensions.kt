package com.mohaberabi.jethabbit.core.util.extensions


fun Int.asClock(): String =
    if (this <= 9) {
        "0${this}"
    } else "$this"