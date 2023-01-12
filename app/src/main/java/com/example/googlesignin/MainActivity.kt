package com.example.googlesignin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.googlesignin.databinding.ActivityMainBinding
import com.example.googlesignin.listener.ISignInResult
import com.example.googlesignin.models.SignInResult

class MainActivity : AppCompatActivity(), ISignInResult {

    private lateinit var binding: ActivityMainBinding
    var googleSinIn: GoogleSignIn? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // past your googleClient id hear
        val googleClientId = ""


        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        googleSinIn = GoogleSignIn(this, googleClientId, this)
        googleSinIn?.init()
        binding.googleSignInButton.setOnClickListener {
            googleSinIn?.signIn()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleSinIn?.onActivityResult(requestCode, data)
    }


    override fun onGotAnException(exception: Exception) {
        Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
    }

    override fun onGetTheSignInResult(signInResult: SignInResult?) {

    }

    override fun onTheCanceledExceptionHappened() {

    }

    override fun onNothingHappened() {

    }
}