package com.example.googlesignin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.example.googlesignin.listener.IGetIDToken
import com.example.googlesignin.listener.IGetUserNamePassword
import com.example.googlesignin.listener.INothingHappened
import com.example.googlesignin.listener.ISignInResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes

class GoogleSignIn constructor(
    val activiy: Activity,
    googleClientId: String,
    var iSignInResult: ISignInResult
) {

    private val TAG: String = "GoogleSignIn"
    private val REQ_ONE_TAP: Int = 100
    private var oneTapClient: SignInClient = Identity.getSignInClient(activiy)
    private var signInRequest: BeginSignInRequest = BeginSignInRequest.builder()
        .setPasswordRequestOptions(
            BeginSignInRequest.PasswordRequestOptions.builder()
                .setSupported(true)
                .build()
        )
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                // Your server's client ID, not your Android client ID.
                .setServerClientId(googleClientId)
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        // Automatically sign in when exactly one credential is retrieved.
        .setAutoSelectEnabled(true)
        .build()

    fun signIn() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(activiy) { result ->
                try {
                    activiy.startIntentSenderForResult(
                        result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null, 0, 0, 0, null
                    )
                } catch (e: IntentSender.SendIntentException) {
                    iSignInResult.onGotAnException(e)
                }
            }
            .addOnFailureListener(activiy) { e ->
                iSignInResult.onGotAnException(e)
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
            }
    }
    fun onActivityResult(requestCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    val username = credential.id
                    val password = credential.password
                    when {
                        idToken != null -> {
                            if (iSignInResult is IGetIDToken) {
                                (iSignInResult as IGetIDToken).onGetIdToken(idToken)
                            }
                        }
                        password != null -> {
                            if (iSignInResult is IGetUserNamePassword) {
                                (iSignInResult as IGetUserNamePassword).onGetUserNamePassword(
                                    username,
                                    password
                                )
                            }
                        }
                        else -> {
                            if (iSignInResult is INothingHappened) {
                                (iSignInResult as INothingHappened).onNothingHappened()
                            }
                        }
                    }
                } catch (e: ApiException) {
                    iSignInResult.onGotAnException(e)
                    when (e.statusCode) {
                        CommonStatusCodes.CANCELED -> {
                            Log.d(TAG, "One-tap dialog was closed.")
                            // Don't re-prompt the user.

                        }
                        CommonStatusCodes.NETWORK_ERROR -> {
                            Log.d(TAG, "One-tap encountered a network error.")
                            // Try again or just ignore.
                        }
                        else -> {
                            Log.d(
                                TAG, "Couldn't get credential from result." +
                                        " (${e.localizedMessage})"
                            )
                        }
                    }
                }
            }
        }
    }
}