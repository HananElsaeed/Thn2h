package com.hananelsaid.hp.thn2h

import android.content.Context
import android.net.ConnectivityManager
import com.hananelsaid.hp.thn2h.SignInPackage.SignInView.SignInActivity
import com.hananelsaid.hp.thn2h.SignInPackage.SignInViewModel.SignInViewModel

class ChechInternerConnection() {
    companion object {
        internal var signViewModel: SignInActivity? = null

        fun isNetworkAvailable(): Boolean {
            signViewModel = SignInActivity()
            val connectivityManager =
                signViewModel!!.passContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}