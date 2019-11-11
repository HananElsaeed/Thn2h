package com.hananelsaid.hp.thn2h.SignInPackage.SignInRepository

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.auth.FirebaseAuth
import com.hananelsaid.hp.thn2h.LoginPackage.LoginView.LoginActivity
import com.hananelsaid.hp.thn2h.SignInPackage.SignInViewModel.SignInViewModel
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.hananelsaid.hp.thn2h.ChechInternerConnection
import com.hananelsaid.hp.thn2h.SignInPackage.SignInView.SignInActivity


class SignInRepository {
    private var auth: FirebaseAuth
    //shared preference refrenc
    lateinit var editor: SharedPreferences.Editor
    val MY_PREFS_NAME = "saveName"

    internal var signViewModel: SignInViewModel? = null


    constructor() {
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()


    }

    constructor(signViewModel: SignInViewModel) {
        auth = FirebaseAuth.getInstance()
        this.signViewModel = signViewModel
    }


    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    fun signin(email: String, password: String) {
        async(email, password).execute()
    }

    private fun verifyEmail() {
        var user = auth?.currentUser
        user!!.sendEmailVerification().addOnCompleteListener {
            if (it.isSuccessful) {
                var openLoginActivity =
                    Intent(signViewModel!!.passContext(), LoginActivity::class.java)
                signViewModel!!.passContext().startActivity(openLoginActivity)
            } else
                signViewModel!!.display(it.exception.toString())
        }

    }


    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    inner class async : AsyncTask<Void, Void, Void> {
        var email: String? = null
        var password: String? = null

        constructor(email: String, password: String) {
            this.email = email
            this.password = password
        }

        override fun doInBackground(vararg p0: Void?): Void? {

            auth.createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //put data to shared prefrence
                        editor = signViewModel!!.passContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit()
                        editor.putString("email", email)
                        editor.putString("password", password)
                        editor.apply()

                        verifyEmail()
                        var currentUser = auth.currentUser
                        signViewModel!!.display("successful")



                    } else {
                        // If sign in fails, display a message to the user.
                        if (!ChechInternerConnection.isNetworkAvailable(signViewModel!!.passContext())) {
                            signViewModel!!.display("Please check the internet connection")
                        }
                        else signViewModel!!.display(task.exception!!.message.toString())


                    }
                }
            return null
        }


    }





}