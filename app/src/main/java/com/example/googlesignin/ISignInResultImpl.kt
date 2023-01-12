package com.example.googlesignin

import com.example.googlesignin.listener.ISignInResult
import com.example.googlesignin.models.SignInResult

class ISignInResultImpl:ISignInResult {
    override fun onGetTheSignInResult(signInResult: SignInResult?) {
        TODO("Not yet implemented")
    }

    override fun onTheCanceledExceptionHappened() {
        TODO("Not yet implemented")
    }

    override fun onGotAnException(exception: Exception) {
        TODO("Not yet implemented")
    }

    override fun onNothingHappened() {
        TODO("Not yet implemented")
    }
}