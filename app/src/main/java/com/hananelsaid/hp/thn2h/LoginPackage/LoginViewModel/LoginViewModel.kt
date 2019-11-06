package com.hananelsaid.hp.thn2h.LoginPackage.LoginViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.hananelsaid.hp.thn2h.LoginPackage.LoginRepository.LoginRepository
import com.hananelsaid.hp.thn2h.LoginPackage.LoginView.LoginActivity

class LoginViewModel :ViewModel{
    var viewRef: LoginActivity? = null
    var repoRef: LoginRepository? = null




    constructor(viewRef: LoginActivity) {
        this.viewRef = viewRef
        repoRef = LoginRepository(this)

    }

    fun passContext(): Context
    {
        return viewRef!!.passContext()
    }

    fun LogIn(email: String, password: String) {
        repoRef!!.userlogin(email, password)

    }

    fun display(messages: String) {
        viewRef!!.display(messages)
    }

    fun loginSuccessfully(){
        viewRef!!.loginSuccessfully()
    }


}