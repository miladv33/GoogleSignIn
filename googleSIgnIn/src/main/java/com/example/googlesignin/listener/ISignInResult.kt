package com.example.googlesignin.listener

import com.example.googlesignin.models.SignInResult

interface ISignInResult {
    fun onGetTheSignInResult(signInResult: SignInResult?)

    fun onTheCanceledExceptionHappened()

    fun onGotAnException(exception: Exception)

    fun onNothingHappened()
}
