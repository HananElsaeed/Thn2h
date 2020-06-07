package com.hananelsaid.hp.thn2h.groups

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass

class GroupRepositroy {
    fun loadGroups(ref: DatabaseReference): MutableLiveData<ArrayList<GroupClass>> {
        return FirebaseUtil.viewGroup(ref)
    }
}