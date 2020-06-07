package com.hananelsaid.hp.thn2h.LoginPackage.LoginView

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.hananelsaid.hp.thn2h.HomePackage.HomeView.HomeActivity
import com.hananelsaid.hp.thn2h.LoginPackage.LoginViewModel.LoginViewModel
import com.hananelsaid.hp.thn2h.R
import com.hananelsaid.hp.thn2h.SignInPackage.SignInView.SignInActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var email: String = ""
    var password: String = ""

    var viewModelRef: LoginViewModel? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 123
    private var mAuth: FirebaseAuth? = null


    @RequiresApi(Build.VERSION_CODES.FROYO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModelRef =
            ViewModelProviders.of(this, LoginViewModelFactory(this)).get(LoginViewModel::class.java)

        btnLogin.setOnClickListener {
            email = etloginemail.text!!.toString().trim()
            password = etloginpassword.text!!.toString().trim()
            LoginUser()
            if (!email.isEmpty() && !password.isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(etloginemail.text!!.toString()).matches()
            ) {
                viewModelRef!!.LogIn(email, password)
            }
        }

        tvopenSignIn.setOnClickListener {
            var intentOpenSignInActivity = Intent(this, SignInActivity::class.java)
            startActivity(intentOpenSignInActivity)
            this.finish()
        }
        mAuth = FirebaseAuth.getInstance()
        createRequest()
        ivGoogle.setOnClickListener {
            signWithGoogle()
        }


    }


    private fun createRequest() {


        // Configure Google Sign In
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signWithGoogle() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account =
                    task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser = mAuth!!.getCurrentUser()!!
                        /*   Intent intent = new Intent(getApplicationContext(),Profile.class);
                                        startActivity(intent);*/Toast.makeText(
                            this, "successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        openHome()
                    } else {
                        Toast.makeText(this, "Sorry auth failed.", Toast.LENGTH_SHORT)
                            .show()
                    }


                    // ...
                })
    }

    private fun openHome() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun LoginUser() {

        if (email.isEmpty()) {
            etloginemail.error = "Please enter the email"
            etloginemail.requestFocus()
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etloginemail.text!!.toString()).matches()) {
            etloginemail.error = "Please enter valid email"
            etloginemail.requestFocus()
        }
        if (password.isEmpty()) {
            etloginpassword.error = "Please enter the password"
            etloginpassword.requestFocus()
        }

    }


    fun display(message: String) {
        Toast.makeText(applicationContext, "" + message, Toast.LENGTH_LONG).show()
    }

    fun passContext(): Context {
        return this
    }

    fun loginSuccessfully() {
        var intentToMainActivity = Intent(this, HomeActivity::class.java)
        startActivity(intentToMainActivity)
        this.finish()
    }


    internal inner class LoginViewModelFactory : ViewModelProvider.Factory {
        private var loginActivity: LoginActivity

        constructor(loginActivity: LoginActivity) {
            this.loginActivity = loginActivity
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(loginActivity) as T
        }
    }
}
