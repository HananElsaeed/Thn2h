package com.hananelsaid.hp.thn2h

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.hananelsaid.hp.thn2h.HomePackage.HomeView.HomeActivity
import com.hananelsaid.hp.thn2h.LoginPackage.LoginView.LoginActivity
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ActivityCompat
import android.content.DialogInterface
import android.os.Build
import android.annotation.TargetApi
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {
    //firebase
    private var auth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isNetworkAvailable()){

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

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}
