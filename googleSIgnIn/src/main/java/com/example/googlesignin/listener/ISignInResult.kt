package com.example.googlesignin.listener

import com.example.googlesignin.models.SignInResult
import com.google.android.gms.common.api.ApiException

interface ISignInResult {
    fun onGetTheSignInResult(signInResult: SignInResult?)

    fun onTheCanceledExceptionHappened()

    fun onGotAnException(exception: Exception)

    fun onNothingHappened()
}