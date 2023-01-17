package com.example.googlesignin.new

import android.app.Activity
import android.content.IntentSender
import com.example.googlesignin.listener.ISignInResult
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient

/**
 * This class will handle the SignInClient and begin the sign-in process.
 * @property activity
 * @property googleSignInRequest
 * @property iSignInResult
 * @property REQ_ONE_TAP
 * @constructor Create empty Google sign in client
 */
class GoogleSignInClient constructor(
    private val activity: Activity,
    private val googleSignInRequest: GoogleSignInRequest,
    var iSignInResult: ISignInResult,
    val REQ_ONE_TAP: Int
) {
    private var oneTapClient: SignInClient? = null

    init {
        oneTapClient = Identity.getSignInClient(activity)
    }

    fun signIn() {
        googleSignInRequest.getSignInRequest()?.let {
            oneTapClient?.beginSignIn(it)?.let { task ->
                task.addOnSuccessListener(activity) { result ->
                    onSuccessToShowGoogleSignIn(result, activity)
                }
                task.addOnFailureListener(activity) { e ->
                    iSignInResult.onGotAnException(e)
                }
            }
        }
    }

    private fun onSuccessToShowGoogleSignIn(result: BeginSignInResult, activity: Activity) {
        try {
            startIntent(activity, result)
        } catch (e: IntentSender.SendIntentException) {
            iSignInResult.onGotAnException(e)
        }
    }

    private fun startIntent(activity: Activity, result: BeginSignInResult) {
        activity.startIntentSenderForResult(
            result.pendingIntent.intentSender, REQ_ONE_TAP,
            null, 0, 0, 0, null
        )
    }
}
