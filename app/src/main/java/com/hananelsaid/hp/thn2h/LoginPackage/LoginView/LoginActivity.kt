package com.hananelsaid.hp.thn2h.LoginPackage.LoginView

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.hananelsaid.hp.thn2h.HomePackage.HomeView.HomeActivity
import com.hananelsaid.hp.thn2h.LoginPackage.LoginViewModel.LoginViewModel
import com.hananelsaid.hp.thn2h.R
import com.hananelsaid.hp.thn2h.SignInPackage.SignInView.SignInActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var email: String = ""
    var password: String = ""

    var viewModelRef: LoginViewModel? = null


    @RequiresApi(Build.VERSION_CODES.FROYO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModelRef = ViewModelProviders.of(this, LoginViewModelFactory(this)).get(LoginViewModel::class.java)

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
            var intentOpenSignInActivity= Intent(this, SignInActivity::class.java)
            startActivity(intentOpenSignInActivity)
            this.finish()
        }

        ivGoogle.setOnClickListener {
           // signWithGoogle()
        }


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
