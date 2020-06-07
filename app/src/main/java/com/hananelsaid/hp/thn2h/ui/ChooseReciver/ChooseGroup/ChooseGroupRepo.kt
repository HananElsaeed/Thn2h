package com.hananelsaid.hp.thn2h.ui.ChooseReciver.ChooseGroup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact


class ChooseGroupRepo {

    fun loadGroups(ref: DatabaseReference): MutableLiveData<ArrayList<GroupClass>> {
        return Groups.viewGroup(ref)
    }

}

object Groups {
    var liveData = MutableLiveData<ArrayList<GroupClass>>()

    fun viewGroup(ref: DatabaseReference): MutableLiveData<ArrayList<GroupClass>> {

        var groupsList = ArrayList<GroupClass>()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                groupsList.clear()
                var group: GroupClass
                for (groupSnapshot in dataSnapshot.children) {
                    //var group  = groupSnapshot.getValue<GroupClass>(GroupClass::class.java)
                    val value = groupSnapshot.value as HashMap<String, Any>

                    Log.i("FireUtilL", value.get("userid").toString())
                   // if (value!!.get("userid") == FirebaseAuth.getInstance().currentUser!!.uid) {
                        val groupName: String = value.get("groupName").toString()
                        val groupMembers = value.get("groupMembers") as java.util.ArrayList<Contact>

                        Log.i("nj",""+groupMembers)

                        group = GroupClass(value.get("groupId").toString(), groupName, groupMembers)

                        groupsList.add(group)


                }
                liveData.setValue(groupsList)
            }
        })
        return liveData

    }

}