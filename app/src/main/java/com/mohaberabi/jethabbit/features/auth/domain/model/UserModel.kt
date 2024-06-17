package com.mohaberabi.jethabbit.features.auth.domain.model

data class UserModel(
    val uid: String,
    val name: String,
    val lastname: String,
    val email: String,
) {
    companion object {
        val empty = UserModel("", "", "", "")
    }
}
