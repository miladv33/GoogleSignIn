package com.example.googlesignin.models

import android.net.Uri

data class SignInResult(
    val idToken: String? = null,
    val userName: String? = null,
    val password: String? = null,
    val profilePictureUri: Uri? = null,
    val displayName: String? = null,
    val familyName: String? = null,
    val givenName: String? = null,
    val phoneNumber: String? = null
)
