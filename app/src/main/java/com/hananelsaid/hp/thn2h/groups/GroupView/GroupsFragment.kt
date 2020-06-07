package com.hananelsaid.hp.thn2h.groups.GroupView

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsView.CreatGroupActivity
import com.hananelsaid.hp.thn2h.R
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import com.hananelsaid.hp.thn2h.groups.GroupsViewModel
import java.util.ArrayList

class GroupsFragment : Fragment() {

    private lateinit var groupsViewModel: GroupsViewModel
    private lateinit var fabaddgroup: FloatingActionButton
    var groupsList: ArrayList<GroupClass> = ArrayList()
    lateinit var databaseGroups: DatabaseReference
    private var adapterClass: GroupsAdapter? = null
    private var recyclerView: RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_groups, container, false)
       recyclerView = root.findViewById(R.id.groups_list)
        fabaddgroup = root.findViewById(R.id.fabaddgroup)
        databaseGroups = FirebaseDatabase.getInstance().getReference("groups")
            .child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())

        setAdapter()

        return root
    }

    private fun setAdapter() {
        val manager = LinearLayoutManager(activity)
        recyclerView!!.setLayoutManager(manager)
        adapterClass = GroupsAdapter()
        recyclerView!!.setAdapter(adapterClass)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fabaddgroup.setOnClickListener {
            var openCreatGroup: Intent = Intent(activity, CreatGroupActivity::class.java)
            activity!!.startActivity(openCreatGroup)

        }
         groupsViewModel = ViewModelProviders.of(this).get(GroupsViewModel::class.java)


    }

    override fun onStart() {
        super.onStart()
        loadGroups()
    }

    fun loadGroups() {
         groupsViewModel.loadGroups(databaseGroups).observe(this, Observer {
             adapterClass!!.setData(it)
         })


    }


}
