package com.hananelsaid.hp.thn2h.contacts.ContactsViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import com.hananelsaid.hp.thn2h.contacts.ContactsRepository.ContactsRepository
import com.hananelsaid.hp.thn2h.contacts.ContactsViews.ContactsFragment

class ContactsViewModel : ViewModel {
    var viewRef: ContactsFragment? = null
   // var creatGroupActivityRef:CreatGroupActivity?=null
    var repoRef: ContactsRepository? = null
    private lateinit var mutableLiveData: MutableLiveData<List<Contact>>


    constructor(viewRef: ContactsFragment) {
        this.viewRef = viewRef
        repoRef = ContactsRepository(this)
        mutableLiveData = MutableLiveData()

    }



    fun getAllOrders(): MutableLiveData<ArrayList<Contact>> {
       // val contactsList = repoRef!!.getContactsList()
        mutableLiveData.postValue(repoRef!!.getContactsList(viewRef!!.activity!!))

        return mutableLiveData as MutableLiveData<ArrayList<Contact>>
    }

/*    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text*/


}