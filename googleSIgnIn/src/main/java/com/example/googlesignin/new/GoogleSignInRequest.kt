package com.example.googlesignin.new

import com.google.android.gms.auth.api.identity.BeginSignInRequest

/**
 * this class will be responsible for creating the sign-in request and handling the request options,
 * such as password and google id token request options
 * @property googleClientId
 * @constructor Create empty Google sign in request
 */
class GoogleSignInRequest constructor(private val googleClientId: String) {
    private var signInRequest: BeginSignInRequest? = null

    init {
        signInRequest = BeginSignInRequest
            .builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(googleClientId)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    fun getSignInRequest(): BeginSignInRequest? {
        return signInRequest
    }
}
