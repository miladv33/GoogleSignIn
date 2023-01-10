package com.example.googlesignin

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.googlesignin.databinding.ActivityMainBinding
import com.example.googlesignin.listener.IGetIDToken

class MainActivity : AppCompatActivity(), IGetIDToken {

    private lateinit var binding: ActivityMainBinding

    var googleSinIn: GoogleSignIn? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // past your googleClient id hear
        val googleClientId = ""


        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        googleSinIn = GoogleSignIn(this, googleClientId, this)

        binding.googleSignInButton.setOnClickListener {
            googleSinIn?.signIn()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleSinIn?.onActivityResult(requestCode, data)
    }

    override fun onGetIdToken(idToken: String) {
        Toast.makeText(this, idToken, Toast.LENGTH_LONG).show()
    }

    override fun onGotAnException(exception: Exception) {
        Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
    }
}