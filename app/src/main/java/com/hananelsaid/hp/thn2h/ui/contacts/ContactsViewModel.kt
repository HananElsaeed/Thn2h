package com.hananelsaid.hp.thn2h.ui.contacts

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hananelsaid.hp.thn2h.ui.contacts.ContactsRepository.ContactsRepository

class ContactsViewModel : ViewModel {
    var viewRef: ContactsFragment? = null
    var repoRef: ContactsRepository? = null

    constructor(viewRef: ContactsFragment) {
        this.viewRef = viewRef
        repoRef = ContactsRepository(this)

    }

    fun passContext(): Context {
        return viewRef!!.passContext()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text


}