import android.app.Activity
import android.content.IntentSender
import com.example.googlesignin.listener.ISignInResult
import com.example.googlesignin.new.GoogleSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient

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

    private fun onSuccessToShowGoogleSignIn(result: Any?, activity: Activity) {
        if (result is IntentSender.SendIntentException) {
            iSignInResult.onGotAnException(result)
        } else {
            signIn()
        }
    }

    private fun startIntent(activity: Activity, result: BeginSignInResult) {
        activity.startIntentSenderForResult(
            result.pendingIntent.intentSender, REQ_ONE_TAP,
            null, 0, 0, 0, null
        )
    }
}
