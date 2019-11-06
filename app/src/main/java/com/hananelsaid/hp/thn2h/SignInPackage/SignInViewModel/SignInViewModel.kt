package com.hananelsaid.hp.thn2h.SignInPackage.SignInViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.hananelsaid.hp.thn2h.SignInPackage.SignInRepository.SignInRepository
import com.hananelsaid.hp.thn2h.SignInPackage.SignInView.SignInActivity

class SignInViewModel : ViewModel {
    var viewRef: SignInActivity? = null
    var repoRef: SignInRepository? = null


    constructor(viewRef: SignInActivity) {
        this.viewRef = viewRef
        repoRef = SignInRepository(this)

    }

    fun passContext(): Context {
        return viewRef!!.passContext()
    }

    fun signIn(email: String, password: String) {
        repoRef!!.signin(email, password)

    }

    fun display(messages: String) {
        viewRef!!.display(messages)
    }
}