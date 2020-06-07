package com.hananelsaid.hp.thn2h

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.hananelsaid.hp.thn2h.HomePackage.HomeView.HomeActivity
import com.hananelsaid.hp.thn2h.LoginPackage.LoginView.LoginActivity


class SplashActivity : AppCompatActivity() {
    //firebase
    private var auth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!CheckInternerConnection.isNetworkAvailable(this)){

        }
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            if(auth!!.currentUser!=null){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            else
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1000)


    }

    override fun onStart() {
        super.onStart()
       // requestContactPermission()
    }



}
