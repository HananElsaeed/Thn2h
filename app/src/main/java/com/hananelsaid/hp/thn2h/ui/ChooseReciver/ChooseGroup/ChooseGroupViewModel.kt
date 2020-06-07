package com.hananelsaid.hp.thn2h.ui.ChooseReciver.ChooseGroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.groups.GroupRepositroy
import com.hananelsaid.hp.thn2h.groups.GroupView.GroupsFragment

class ChooseGroupViewModel : ViewModel {


    lateinit var groupsRepo: ChooseGroupRepo
    lateinit var groupsViewRef: ChooseGroupFragment
    private lateinit var mutableLiveData: MutableLiveData<ArrayList<GroupClass>>


    constructor(){
        groupsRepo = ChooseGroupRepo()
        mutableLiveData = MutableLiveData()
    }

    fun loadGroups(ref: DatabaseReference):MutableLiveData<ArrayList<GroupClass>>{
        return groupsRepo.loadGroups(ref)
    }


}