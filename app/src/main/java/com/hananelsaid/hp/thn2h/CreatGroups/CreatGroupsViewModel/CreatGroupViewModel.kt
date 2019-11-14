package com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsRepo.CreatGroupRepo
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsView.CreatGroup
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import com.hananelsaid.hp.thn2h.contacts.ContactsRepository.ContactsRepository

class CreatGroupViewModel : ViewModel {


    var creatGroupRef: CreatGroup? = null
    var repoRef: ContactsRepository? = null
    var creatGroupRepo: CreatGroupRepo? = null
    private lateinit var mutableLiveData: MutableLiveData<List<Contact>>


    constructor(creatGroupRef: CreatGroup) {
        this.creatGroupRef = creatGroupRef
        creatGroupRepo = CreatGroupRepo(this)
        repoRef = ContactsRepository(this)
        mutableLiveData = MutableLiveData()

    }

    fun uploadGroup(group: GroupClass) {
        Log.i("nnn", "يارب يشتغل")
        creatGroupRepo!!.loadgroupToFirebase(group)
    }


    fun getAllOrders(): MutableLiveData<ArrayList<Contact>> {
        // val contactsList = repoRef!!.getContactsList()
        mutableLiveData.postValue(repoRef!!.getContactsList(creatGroupRef!!))

        return mutableLiveData as MutableLiveData<ArrayList<Contact>>
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

}