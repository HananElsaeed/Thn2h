package com.hananelsaid.hp.thn2h.SignInPackage.SignInView

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.hananelsaid.hp.thn2h.LoginPackage.LoginView.LoginActivity
import com.hananelsaid.hp.thn2h.R
import com.hananelsaid.hp.thn2h.SignInPackage.SignInViewModel.SignInViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    var email: String = ""
    var password: String = ""
    var conpassword: String = ""
    var viewModelRef: SignInViewModel? = null
    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.FROYO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        viewModelRef =
            ViewModelProviders.of(this, SignViewModelFactory(this)).get(SignInViewModel::class.java)

        //open login activity
        tvopenLogin.setOnClickListener {
            var openLoginIntent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(openLoginIntent)
            this.finish()
        }

        //signIn button action
        btnSignIn.setOnClickListener {
            email = etemail.text!!.toString().trim()
            password = etpassword.text!!.toString().trim()
            conpassword = etconfirmpassword.text.toString().trim()
            singnInUser()

            if (!email.isEmpty() && !password.isEmpty() &&
                !conpassword.isEmpty() && password.equals(conpassword) &&
                Patterns.EMAIL_ADDRESS.matcher(etemail.text!!.toString()).matches()
            ) {
                viewModelRef!!.signIn(email, password)
            }


        }
    }

    fun display(message: String) {
        Toast.makeText(applicationContext, "" + message, Toast.LENGTH_LONG).show()
    }

    fun passContext(): Context {
        return this
    }


    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun singnInUser() {


        if (email.isEmpty()) {
            etemail.error = "Please enter the email"
            etemail.requestFocus()
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etemail.text!!.toString()).matches()) {
            etemail.error = "Please enter valid email"
            etemail.requestFocus()
        }
        if (password.isEmpty()) {
            etpassword.error = "Please enter the password"
            etpassword.requestFocus()
        }

        if (!password.equals(conpassword)) {
            etconfirmpassword.error = "Not the same password"
            etconfirmpassword.requestFocus()

        }
    }

    internal inner class SignViewModelFactory : ViewModelProvider.Factory {
        private var signinActivity: SignInActivity

        constructor(signinActivity: SignInActivity) {
            this.signinActivity = signinActivity

        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignInViewModel(signinActivity) as T
        }
    }
}
