package com.hananelsaid.hp.thn2h.HomePackage.HomeView

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.hananelsaid.hp.thn2h.LoginPackage.LoginView.LoginActivity

import androidx.annotation.RequiresApi


class HomeActivity : AppCompatActivity() {
    val PERMISSIONS_REQUEST_READ_CONTACTS = 1
    private val MY_PERMISSIONS_REQUEST_SEND_SMS = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hananelsaid.hp.thn2h.R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(com.hananelsaid.hp.thn2h.R.id.nav_view)

        val navController = findNavController(com.hananelsaid.hp.thn2h.R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                com.hananelsaid.hp.thn2h.R.id.navigation_pending,
                com.hananelsaid.hp.thn2h.R.id.navigation_groups,
                com.hananelsaid.hp.thn2h.R.id.navigation_completed
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    //menu to sinout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.hananelsaid.hp.thn2h.R.menu.main_menu, menu)

        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        requestContactPermission()
        checkForSmsPermission()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id = item!!.itemId
        if (id == com.hananelsaid.hp.thn2h.R.id.logout) {
            FirebaseAuth.getInstance().signOut()
            var intentLogout = Intent(this, LoginActivity::class.java)
            startActivity(intentLogout)
            finish()
        }
        return true
    }
    fun requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.READ_CONTACTS
                    )
                ) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Read Contacts permission")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage("Please enable access to contacts.")
                    builder.setOnDismissListener(DialogInterface.OnDismissListener {
                        requestPermissions(
                            arrayOf(android.Manifest.permission.READ_CONTACTS),
                            PERMISSIONS_REQUEST_READ_CONTACTS
                        )
                    })
                    builder.show()
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_CONTACTS),
                        PERMISSIONS_REQUEST_READ_CONTACTS
                    )
                }
            } else {
                return
            }
        } else {
            return
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.SEND_SMS
                )
            ) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Send SMS permission")
                builder.setPositiveButton(android.R.string.ok, null)
                builder.setMessage("Please enable access to SMS")
                builder.setOnDismissListener(DialogInterface.OnDismissListener {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.SEND_SMS),
                        MY_PERMISSIONS_REQUEST_SEND_SMS
                    )
                })
                builder.show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.SEND_SMS),
                    MY_PERMISSIONS_REQUEST_SEND_SMS
                )
            }
        } else {
            // Permission already granted. Enable the SMS button.
         return
        }
    }
}
