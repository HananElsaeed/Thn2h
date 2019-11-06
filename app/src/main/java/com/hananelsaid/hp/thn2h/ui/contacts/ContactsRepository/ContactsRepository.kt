package com.hananelsaid.hp.thn2h.ui.contacts.ContactsRepository

import android.database.Cursor
import android.os.Bundle
import com.hananelsaid.hp.thn2h.ui.contacts.ContactsViewModel
import android.provider.ContactsContract
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader

class ContactsRepository {

    var contactViewModel: ContactsViewModel


    constructor(contactViewModel: ContactsViewModel) {
        this.contactViewModel = contactViewModel
    }


    fun getContactsList (){

    }




}