package com.example.googlesignin

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import com.example.googlesignin.listener.ISignInResult

import com.example.googlesignin.models.SignInRequirements
import com.example.googlesignin.models.SignInResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes


@Deprecated("")
class GoogleSignIn constructor(
    val activiy: Activity,
    val googleClientId: String,
    var iSignInResult: ISignInResult
) {

    private val REQ_ONE_TAP: Int = 100
    private val signInRequirements: SignInRequirements? = null

    init {
        initilize(signInRequirements?.getSignRequirements())
    }

    fun initilize(signInRequirements: SignInRequirements?) {
        signInRequirements?.let {
            signInRequirements.oneTapClient = Identity.getSignInClient(activiy)
            signInRequirements.signInRequest = signInRequirements.BeginSignInRequest
                ?.setPasswordRequestOptions(
                    BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build()
                )
                ?.setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(googleClientId)
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                // Automatically sign in when exactly one credential is retrieved.
                ?.setAutoSelectEnabled(true)
                ?.build()
        }
    }

    fun signIn() {
        signInRequirements?.signInRequest?.let { signInRequest ->
            signInRequirements.oneTapClient?.beginSignIn(signInRequest)
                ?.addOnSuccessListener(activiy) { result ->
                    onSuccessToShowGoogleSignIn(result, activiy)
                }
                ?.addOnFailureListener(activiy) { e ->
                    iSignInResult.onGotAnException(e)

                }
        }
    }

    fun onSuccessToShowGoogleSignIn(result: BeginSignInResult, activiy: Activity) {
        try {
            startIntent(activiy, result)
        } catch (e: IntentSender.SendIntentException) {
            iSignInResult.onGotAnException(e)
        }
    }

    private fun startIntent(
        activiy: Activity,
        result: BeginSignInResult
    ) {
        activiy.startIntentSenderForResult(
            result.pendingIntent.intentSender, REQ_ONE_TAP,
            null, 0, 0, 0, null
        )
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
            setResult(getCredential(signInRequirements?.oneTapClient, data))
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