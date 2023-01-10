package com.example.googlesignin.listener

interface INothingHappened : ISignInResult {
    // Shouldn't happen.
    fun onNothingHappened()
}