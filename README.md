# Using Google Sign-In API in your project is easy with this library.

The following image shows how Google authentication works 

![](https://developers.google.com/static/identity/sign-in/android/images/google-sign-in.png)

# How to Use it?
These lines need to be added to your Gradle files

```   maven { url 'https://jitpack.io' } ```

Then

```     implementation 'com.github.miladv33:GoogleSIgnIn:1.0.0' ```

Then go to your Activity and follow these steps:
 
```
class MainActivity : AppCompatActivity(), IGetIDToken {
    var googleSignIn: GoogleSignIn? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val googleClientId = ""
        googleSignIn = GoogleSignIn(this, googleClientId, this)
        googleSignIn?.signIn()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleSignIn?.onActivityResult(requestCode, data)
    }

    override fun onGetIdToken(idToken: String) {
        Toast.makeText(this, idToken, Toast.LENGTH_LONG).show()
    }

    override fun onGotAnException(exception: Exception) {
        Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
    }
}
```
# Note:
Your client ID can be found in Google Fierbase 
