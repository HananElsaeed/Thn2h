package com.hananelsaid.hp.thn2h.LoginPackage.LoginRepository

import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.hananelsaid.hp.thn2h.CheckInternerConnection
import com.hananelsaid.hp.thn2h.LoginPackage.LoginViewModel.LoginViewModel

class LoginRepository {

    val MY_PREFS_NAME = "saveName"
    //firebase
    private var auth: FirebaseAuth? = null

    //view model refrence
    internal var loginViewModel: LoginViewModel? = null

    constructor() {
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }

    constructor(loginViewModel: LoginViewModel) {
        auth = FirebaseAuth.getInstance()
        this.loginViewModel = loginViewModel
    }


    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    fun userlogin(email: String, password: String) {
        FirebaseLoginOperations(email, password).execute()
    }


    // Async
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    internal inner class FirebaseLoginOperations : AsyncTask<Void, Void, Void> {
        var email: String? = null
        var pass: String? = null


        constructor(email: String, pass: String) {
            this.email = email
            this.pass = pass
        }


        override fun doInBackground(vararg voids: Void): Void? {
            auth!!.signInWithEmailAndPassword(email!!, pass!!)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        verifyEmail()

                    } else {
                        if (!CheckInternerConnection.isNetworkAvailable(loginViewModel!!.passContext())) {
                            /* loginViewModel!!.display("Please check the internet connection")*/
                            getEmailPassword(email!!, pass!!)
                        } else
                            loginViewModel!!.display(it.exception!!.message.toString())
                    }
                }
            Log.i("onFailure", "" + email)
            return null
        }
    }

    fun getEmailPassword(mail: String, pass: String) {
        val prefs =
            loginViewModel!!.passContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        var email: String? = prefs.getString("email", "No email defined")
        var password: String? = prefs.getString("password", "No password defined")

        Log.i("tag", email)
        if (mail.equals(email)) {
            if (pass.equals(password)) {





            } else loginViewModel!!.display("Incorect password")
        } else
            loginViewModel!!.display("Please check the internet connection")

    }

    private fun verifyEmail() {
        var user = auth?.currentUser
        if (user!!.isEmailVerified) {
            loginViewModel!!.loginSuccessfully()
        } else
            loginViewModel!!.display("you should verify your email")
    }


}