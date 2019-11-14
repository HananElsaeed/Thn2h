package com.hananelsaid.hp.thn2h.groups

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsView.CreatGroup
import com.hananelsaid.hp.thn2h.DataLoadListener
import com.hananelsaid.hp.thn2h.R
import java.util.ArrayList

class GroupsFragment : Fragment()/*, DataLoadListener*/ {

    //private lateinit var groupsViewModel: GroupsViewModel
    private lateinit var fabaddgroup: FloatingActionButton
    var groupsList: ArrayList<GroupClass> = ArrayList()
    lateinit var databaseGroups: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_groups, container, false)

        fabaddgroup = root.findViewById(R.id.fabaddgroup)


        /*groupsViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fabaddgroup.setOnClickListener {
            var openCreatGroup: Intent = Intent(activity, CreatGroup::class.java)
            activity!!.startActivity(openCreatGroup)

        }
        /*  groupsViewModel = ViewModelProviders.of(this*//*,GroupsViewModelFactory(this)*//*).get(GroupsViewModel::class.java)
        groupsViewModel.initViewModel(this.activity!!)
        val arrayList = groupsViewModel.getGroupsList().value*/

        /*
         groupsViewModel.getGroupsList()!!.observe(this, object : Observer<MutableList<GroupClass>> {
             override fun onChanged(groups: MutableList<GroupClass>?) {
               //  adapterClass!!.setData((contacts as MutableList<GroupClass>?)!!)


             }
         })*/
    }

    override fun onStart() {
        super.onStart()
        loadGroups()
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
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        }).start()

    }


    /*   override fun onGroupLoaded() {

           groupsViewModel.getGroupsList()!!.observe(this, object : Observer<MutableList<GroupClass>> {
               override fun onChanged(groups: MutableList<GroupClass>?) {
                   //  adapterClass!!.setData((contacts as MutableList<GroupClass>?)!!)
                   Log.i("yarab", groups!!.get(0).groupName)


               }
           })


       }*/

/*
    internal inner class GroupsViewModelFactory : ViewModelProvider.Factory {
        private var groupsFragment: GroupsFragment

        constructor(contactFragment: GroupsFragment) {
            this.groupsFragment = contactFragment

        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GroupsViewModel(
                groupsFragment
            ) as T
        }
    }*/
}