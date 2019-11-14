package com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsRepo

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsViewModel.CreatGroupViewModel

class CreatGroupRepo {
    var creatGroupviewModel: CreatGroupViewModel

    var myRef: DatabaseReference


    constructor(creatGroupviewModel: CreatGroupViewModel) {
        this.creatGroupviewModel = creatGroupviewModel

        // Write a message to the database
        //myRef = database.getReference("groups")
        myRef = FirebaseDatabase.getInstance().getReference("groups")


    }

    fun loadgroupToFirebase(group: GroupClass) {

        // val note = Notes(notes, tripId)
        if (group != null) {
            val id = myRef.push().getKey()
           // String id = databaseNotes.push().getKey();
            if(id!=null)
            myRef.child(id).setValue(group)
        }


    }
}