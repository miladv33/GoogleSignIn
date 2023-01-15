package com.example.googlesignin.models

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient

data class SignInRequirements(
    var oneTapClient: SignInClient? = null,
    var signInRequest: BeginSignInRequest? = null,
    var BeginSignInRequest: BeginSignInRequest.Builder? = null
) {

    fun getSignRequirements(): SignInRequirements {
        return SignInRequirements()
    }
}