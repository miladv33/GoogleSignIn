package com.example.googlesignin.listener

interface IGetIDToken : ISignInResult {
    // Got an ID token from Google. Use it to authenticate
    // with your backend.
    fun onGetIdToken(idToken: String)
}