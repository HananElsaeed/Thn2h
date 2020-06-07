package com.hananelsaid.hp.thn2h.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.groups.GroupView.GroupsFragment

class GroupsViewModel : ViewModel {


    lateinit var groupsRepo: GroupRepositroy
    lateinit var groupsViewRef: GroupsFragment
    private lateinit var mutableLiveData: MutableLiveData<ArrayList<GroupClass>>


    constructor(){
        groupsRepo = GroupRepositroy()
        mutableLiveData = MutableLiveData()
    }

    fun loadGroups(ref: DatabaseReference):MutableLiveData<ArrayList<GroupClass>>{
        return groupsRepo.loadGroups(ref)
    }

/*
    fun getGroupsList ():LiveData<ArrayList<GroupClass>>{
        mutableLiveData.postValue(groupsRepoRef!!.getGroupsFromFirebase())

        return mutableLiveData as MutableLiveData<ArrayList<GroupClass>>



    }*/


}