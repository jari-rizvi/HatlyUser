package com.teamx.hatlyUser.utils

import okhttp3.Credentials

object Helper {


    var credentials = Credentials.basic("email@test.com", "test"); //Some initial data

    fun setCredentials(credentials: Credentials) {
        this.credentials = credentials.toString()
    }


    fun getCredential(): String {
        return credentials
    }
}