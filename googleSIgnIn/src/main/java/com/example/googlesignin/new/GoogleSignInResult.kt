package com.example.googlesignin.new

import android.app.Activity
import android.content.Intent
import com.example.googlesignin.listener.ISignInResult
import com.example.googlesignin.models.SignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes

/**
 * This class will handle the result of the sign-in process,
 * parse the result and set it back to the caller through the ISignInResult interface.
 *
 * @property iSignInResult
 * @property activity
 * @property REQ_ONE_TAP
 * @constructor Create empty Google sign in result
 */
class GoogleSignInResult constructor(
    var iSignInResult: ISignInResult,
    val activity: Activity,
    val REQ_ONE_TAP: Int
) {

    fun parseActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == REQ_ONE_TAP) {
            try {
                setResult(getCredential(data))
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
    }

    private fun setResult(credential: SignInCredential?) {
        if (credential != null) {
            iSignInResult.onGetTheSignInResult(
                SignInResult(
                    credential.googleIdToken,
                    credential.id,
                    credential.password,
                    credential.profilePictureUri,
                    credential.displayName,
                    credential.familyName,
                    credential.givenName,
                    credential.phoneNumber
                )
            )
        } else {
            iSignInResult.onNothingHappened()
        }
    }

    private fun getCredential(data: Intent?): SignInCredential? {
        return Identity.getSignInClient(activity).getSignInCredentialFromIntent(data)
    }
}
