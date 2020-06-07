package com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsRepo.CreatGroupRepo
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsView.CreatGroupActivity
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import com.hananelsaid.hp.thn2h.contacts.ContactsRepository.ContactsRepository

class CreatGroupViewModel : ViewModel {


    var creatGroupActivityRef: CreatGroupActivity? = null
    var repoRef: ContactsRepository? = null
    var creatGroupRepo: CreatGroupRepo? = null
    private lateinit var mutableLiveData: MutableLiveData<List<Contact>>


    constructor(creatGroupActivityRef: CreatGroupActivity) {
        this.creatGroupActivityRef = creatGroupActivityRef
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
        mutableLiveData.postValue(repoRef!!.getContactsList(creatGroupActivityRef!!))

        return mutableLiveData as MutableLiveData<ArrayList<Contact>>
    }

 /*   private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text*/

}