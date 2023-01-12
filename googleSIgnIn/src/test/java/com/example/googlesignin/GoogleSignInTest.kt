package com.example.googlesignin

import android.app.Activity
import android.content.Intent
import com.example.googlesignin.listener.ISignInResult
import com.example.googlesignin.models.SignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import io.mockk.*
import org.junit.Before
import org.junit.Test

class GoogleSignInTest {

    @io.mockk.impl.annotations.MockK
    lateinit var googleSignIn: GoogleSignIn
    lateinit var inResult: ISignInResult
    lateinit var activiy: Activity
    lateinit var data: Intent
    lateinit var oneTapClient: SignInClient
    lateinit var signInResult: SignInResult
    @Before
    fun before() {
        activiy = mockk()
        inResult = spyk()
        data = mockk()
        oneTapClient = mockk()
        googleSignIn = GoogleSignIn(activiy, "", inResult)
        signInResult = SignInResult(
            BuildConfig.testToken, "Milad", "",
            mockk(),  "Milad Varvaei", "Varvaei", "Milad", "+980000000"
        )
    }

    @Test
    fun `return result`() {
        every { googleSignIn.getCredential(oneTapClient, data) } returns signInResult
        googleSignIn.setResult(signInResult)
        coVerify { inResult.onGetTheSignInResult(signInResult) }
    }

    @Test
    fun `return null when asking for credential`() {
        googleSignIn.setResult(null)
        coVerify { inResult.onNothingHappened() }
    }

    @Test
    fun `return an canceled(#16) exception`() {
        val apiException = ApiException(Status.RESULT_CANCELED)
        every { googleSignIn.parseActivityResult( data) } throws apiException
        googleSignIn.parseActivityResult(data)
        coVerify { inResult.onTheCanceledExceptionHappened() }
    }

    @Test
    fun `return an NetworkError(#7) exception`() {
        val apiException = ApiException(Status.RESULT_TIMEOUT)
        every { googleSignIn.parseActivityResult( data) } throws apiException
        googleSignIn.parseActivityResult(data)
        coVerify { inResult.onGotAnException(apiException) }
    }
}