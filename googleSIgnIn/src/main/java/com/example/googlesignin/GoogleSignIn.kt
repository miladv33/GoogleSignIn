package com.example.googlesignin

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.example.googlesignin.listener.ISignInResult
import com.example.googlesignin.models.SignInResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes

class GoogleSignIn constructor(
    val activiy: Activity,
    val googleClientId: String,
    var iSignInResult: ISignInResult
) {

    private val TAG: String = "GoogleSignIn"
    private val REQ_ONE_TAP: Int = 100
    private var oneTapClient: SignInClient? = null
    private var signInRequest: BeginSignInRequest? = null

    fun init() {
        oneTapClient = Identity.getSignInClient(activiy)
        signInRequest = BeginSignInRequest.builder()
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
    }

    fun signIn() {
        signInRequest?.let { signInRequest ->
            oneTapClient?.beginSignIn(signInRequest)
                ?.addOnSuccessListener(activiy) { result ->
                    try {
                        activiy.startIntentSenderForResult(
                            result.pendingIntent.intentSender, REQ_ONE_TAP,
                            null, 0, 0, 0, null
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        iSignInResult.onGotAnException(e)
                    }
                }
                ?.addOnFailureListener(activiy) { e ->
                    iSignInResult.onGotAnException(e)
                    // No saved credentials found. Launch the One Tap sign-up flow, or
                    // do nothing and continue presenting the signed-out UI.
                }
        }
    }

    fun onActivityResult(requestCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_ONE_TAP -> {
                parseActivityResult(data)
            }
        }
    }

    fun parseActivityResult(data: Intent?) {
        try {
            setResult(getCredential(oneTapClient, data))
        } catch (e: ApiException) {
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    iSignInResult.onTheCanceledExceptionHappened()
                }
                else -> {
                    iSignInResult.onGotAnException(e)
                }
            }
        }
    }

    fun setResult(signInResult: SignInResult?) {
        when {
            signInResult != null -> {
                iSignInResult.onGetTheSignInResult(signInResult)
            }
            else -> {
                iSignInResult.onNothingHappened()
            }
        }
    }

    fun getCredential(oneTapClient: SignInClient?, intent: Intent?): SignInResult? {
        intent?.let {
            val credential = oneTapClient?.getSignInCredentialFromIntent(intent)
            return SignInResult(
                credential?.googleIdToken,
                credential?.id,
                credential?.password,
                credential?.profilePictureUri,
                credential?.displayName,
                credential?.familyName,
                credential?.givenName,
                credential?.phoneNumber
            )
        }
        return null
    }
}