package com.mohaberabi.jethabbit.core.util.error


object ErrorCodes {
    const val INVALID_EMAIL = "ERROR_INVALID_EMAIL"
    const val USER_DISABLED = "ERROR_USER_DISABLED"
    const val USER_NOT_FOUND = "ERROR_USER_NOT_FOUND"
    const val EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE"
    const val WEAK_PASSWORD = "ERROR_WEAK_PASSWORD"
    const val WRONG_PASSWORD = "ERROR_WRONG_PASSWORD"
    const val INVALID_CUSTOM_TOKEN = "ERROR_INVALID_CUSTOM_TOKEN"
    const val ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL =
        "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL"
    const val PERMISSION_DENIED = "PERMISSION_DENIED"
    const val UNAUTHENTICATED = "UNAUTHENTICATED"
    const val NOT_FOUND = "NOT_FOUND"
    const val ABORTED = "ABORTED"
    const val UNAVAILABLE = "UNAVAILABLE"
    const val DATA_LOSS = "DATA_LOSS"
}