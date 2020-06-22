package com.hananelsaid.hp.thn2h.ui.ChooseReciver.ChooseContacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import com.hananelsaid.hp.thn2h.contacts.ContactsRepository.ContactsRepository

class ChooseContactsViewModel : ViewModel {


    var creatGroupActivityRef: ChooseContactsFragment? = null
    var repoRef: ContactsRepository? = null
   // var creatGroupRepo: CreatGroupRepo? = null
    private lateinit var mutableLiveData: MutableLiveData<List<Contact>>


    constructor(creatGroupActivityRef: ChooseContactsFragment) {
        this.creatGroupActivityRef = creatGroupActivityRef
        //creatGroupRepo = CreatGroupRepo(this)
        repoRef = ContactsRepository(this)
        mutableLiveData = MutableLiveData()

    }


    fun getAllOrders(): MutableLiveData<ArrayList<Contact>> {
        // val contactsList = repoRef!!.getContactsList()

        mutableLiveData.postValue(repoRef!!.getContactsList(creatGroupActivityRef!!.activity!!))

        return mutableLiveData as MutableLiveData<ArrayList<Contact>>
    }
}
