package com.example.googlesignin.listener

interface IGetUserNamePassword : ISignInResult {
    // Got a saved username and password. Use them to authenticate
    // with your backend.
    fun onGetUserNamePassword(userName: String, password: String)
}