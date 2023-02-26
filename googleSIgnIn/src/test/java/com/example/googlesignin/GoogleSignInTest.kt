package com.example.googlesignin

class GoogleSignInTest {

//    @io.mockk.impl.annotations.MockK
//    lateinit var googleSignIn: GoogleSignIn
//    lateinit var inResult: ISignInResult
//    lateinit var activiy: Activity
//    lateinit var data: Intent
//    lateinit var oneTapClient: SignInClient
//    lateinit var signInResult: SignInResult
//    lateinit var signInRequest: BeginSignInRequest
//    lateinit var result: BeginSignInResult
//    lateinit var signInRequirements: SignInRequirements

//    @Before
//    fun before() {
//        activiy = mockk()
//        inResult = spyk()
//        data = mockk()
//        oneTapClient = mockk()
//        signInRequest = mockk()
//        result = mockk()
//        mockkConstructor(SignInRequirements::class)
//        val signInRequirements = mockk<SignInRequirements>()
//        every { anyConstructed<SignInRequirements>().getSignRequirements() } returns signInRequirements
//
//        googleSignIn = GoogleSignIn(activiy, "", inResult)
//
//        signInResult = SignInResult(
//            "", "Milad", "",
//            mockk(),  "Milad Varvaei", "Varvaei", "Milad", "+980000000"
//        )
//    }

//    @Test
//    fun `return result`() {
//        every { googleSignIn.getCredential(oneTapClient, data) } returns signInResult
//        googleSignIn.setResult(signInResult)
//        coVerify { inResult.onGetTheSignInResult(signInResult) }
//    }
//
//    @Test
//    fun `return null when asking for credential`() {
//        googleSignIn.setResult(null)
//        coVerify { inResult.onNothingHappened() }
//    }
//
//    @Test
//    fun `return an canceled(#16) exception`() {
//        val apiException = ApiException(Status.RESULT_CANCELED)
//        every { googleSignIn.parseActivityResult( data) } throws apiException
//        googleSignIn.parseActivityResult(data)
//        coVerify { inResult.onTheCanceledExceptionHappened() }
//    }
//
//    @Test
//    fun `return other ApiException exception`() {
//        val apiException = ApiException(Status.RESULT_TIMEOUT)
//        every { googleSignIn.parseActivityResult( data) } throws apiException
//        googleSignIn.parseActivityResult(data)
//        coVerify { inResult.onGotAnException(apiException) }
//    }
//
//    @Test
//    fun `return SendIntentException on signIn()`() {
//        val sendIntentException = IntentSender.SendIntentException()
//        every { googleSignIn.onSuccessToShowGoogleSignIn(result, activiy) } throws sendIntentException
//        googleSignIn.onSuccessToShowGoogleSignIn(result, activiy)
//        coVerify { inResult.onGotAnException(sendIntentException) }
//    }
}