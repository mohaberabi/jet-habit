package com.mohaberabi.jethabbit.features.auth.data.soruce.remote.dto

import com.mohaberabi.jethabbit.features.auth.domain.model.UserModel

data class UserModelDto(


    val uid: String,
    val name: String,
    val lastname: String,
    val email: String,
) {
    constructor() : this("", "", "", "")
}

fun UserModelDto.toUserModel(): UserModel =
    UserModel(uid = uid, name = name, lastname = lastname, email = email)


fun UserModel.toUserDto(): UserModelDto =
    UserModelDto(uid = uid, name = name, lastname = lastname, email = email)