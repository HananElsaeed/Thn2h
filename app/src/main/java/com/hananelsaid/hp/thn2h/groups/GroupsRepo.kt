package com.hananelsaid.hp.thn2h.groups

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.DataLoadListener
import java.util.ArrayList

class GroupsRepo {
    private lateinit var groupViewModel: GroupsViewModel
    lateinit var databaseGroups: DatabaseReference
     var groupsList: ArrayList<GroupClass> =ArrayList()

  /*  constructor(groupViewModel: GroupsViewModel) {
        this.groupViewModel = groupViewModel
        databaseGroups = FirebaseDatabase.getInstance().getReference("groups")
        groupsList = ArrayList()
    }
    constructor(){}*/

    companion object {
        lateinit var mContext: Context

         var groupinstance: GroupsRepo=GroupsRepo()

        lateinit var dataLoadListener: DataLoadListener

        fun getGroupInstance(context: Context): GroupsRepo {

            this.mContext = context
            if (groupinstance == null)
                groupinstance = GroupsRepo()
            dataLoadListener = mContext as DataLoadListener
            return groupinstance


        }
    }

    fun getGroupsFromFirebase(): MutableLiveData<ArrayList<GroupClass>> {
        loadGroups()
        var groups: MutableLiveData<ArrayList<GroupClass>> = MutableLiveData()
        groups.value = groupsList
        return groups


    }

    fun loadGroups() {

        // var group: GroupClass? = null
        Thread(Runnable {
            databaseGroups.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (tripSnapshot in dataSnapshot.children) {
                        val group = tripSnapshot.getValue<GroupClass>(GroupClass::class.java!!)

                        if (group!!.userid.equals(FirebaseAuth.getInstance().currentUser!!.uid)) {

                            groupsList.add(group)
                        }

                    }
                    dataLoadListener.onGroupLoaded()
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
            //  Thread.sleep(3000)

        }).start()

        /*  if (groupsList.size > 0)
              return groupsList
          else
              Thread.sleep(2000)
          Log.i("me", groupsList!!.get(0).groupName)*/
        // return groupsList
    }
}

