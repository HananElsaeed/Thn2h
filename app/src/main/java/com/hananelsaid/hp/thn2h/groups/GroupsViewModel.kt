package com.hananelsaid.hp.thn2h.groups

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact

class GroupsViewModel : ViewModel() {

    //lateinit var groupsRepoRef: GroupsRepo
    lateinit var groupsViewRef: GroupsFragment
    private lateinit var mutableLiveData: MutableLiveData<ArrayList<GroupClass>>


/*
    constructor(groupsViewRef: GroupsFragment){
        this.groupsViewRef=groupsViewRef
       // groupsRepoRef= GroupsRepo(this)
        //mutableLiveData = MutableLiveData()
        mutableLiveData= GroupsRepo.getGroupInstance(groupsViewRef!!.activity).getGroupsFromFirebase()

    }*/

   fun  initViewModel(context:Context){
        mutableLiveData= GroupsRepo.getGroupInstance(context).getGroupsFromFirebase()
    }


    fun getGroupsList ():LiveData<ArrayList<GroupClass>>{
       // mutableLiveData.postValue(groupsRepoRef!!.getGroupsFromFirebase())

        return mutableLiveData as MutableLiveData<ArrayList<GroupClass>>



    }


    /* private val _text = MutableLiveData<String>().apply {
         value = "This is dashboard Fragment"
     }
     val text: LiveData<String> = _text*/
}