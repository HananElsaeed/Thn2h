package com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsView

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsViewModel.CreatGroupViewModel
import com.hananelsaid.hp.thn2h.HomePackage.HomeView.HomeActivity
import com.hananelsaid.hp.thn2h.R
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import com.hananelsaid.hp.thn2h.contacts.ContactsViews.ContactAdapter
import java.util.*

class CreatGroupActivity : AppCompatActivity(), ContactAdapter.ChnageStatusListener {
    private lateinit var creatGroupViewModel: CreatGroupViewModel
    private var adapterClass: ContactAdapter? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var searchView: SearchView
    private lateinit var floatbtnsave: FloatingActionButton
    private lateinit var etgroupName: TextInputEditText

    //firebase
    private var auth: FirebaseAuth? = null
    private var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_group)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("groups")


        creatGroupViewModel = ViewModelProviders.of(this, CreatGroupViewModelFactory(this)).get(
            CreatGroupViewModel::class.java
        )

        recyclerView = findViewById(R.id.contact_list2)
        searchView = findViewById(R.id.searchView2)


        floatbtnsave = findViewById(R.id.floatbtnsave)
        etgroupName = findViewById(R.id.etgroupName)
        // recyclerView.textCh
        //group = GroupClass()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapterClass!!.getFilter().filter(newText)
                return false
            }
        })


        //save data on firebase
        floatbtnsave.setOnClickListener {

            val groupName = etgroupName.text!!.trim().toString()
            if (!groupName.isEmpty()) {
                val temp = ArrayList<Contact>()
                creatGroupViewModel.getAllOrders()!!.observe(
                    this,
                    object : Observer<MutableList<Contact>> {
                        override fun onChanged(contacts: MutableList<Contact>?) {
                            for (i in contacts!!.indices) {
                                if (contacts.get(i).isSelected()) {
                                    temp.add(contacts.get(i))
                                }
                            }
                        }
                    })

                //Log.i("try", ""+temp.get(0).name)
                if (auth!!.currentUser != null) {
                    // val uid = auth!!.currentUser!!.uid
                    val groupId: String =
                        databaseReference!!.child(auth!!.currentUser!!.uid).child("groups").push()
                            .getKey()!!
                    var group: GroupClass = GroupClass(groupId, groupName, temp)

                    creatGroupViewModel.uploadGroup(group)
                }
                startActivity(Intent(this,HomeActivity::class.java))
                // findNavController(R.id.nav_host_fragment);
                this.finish()

            }
        }
    }

    override fun onStart() {
        super.onStart()
        var temp = ArrayList<Contact>()

        setAdapter()
        creatGroupViewModel.getAllOrders()!!.observe(this, object : Observer<MutableList<Contact>> {

            override fun onChanged(contacts: MutableList<Contact>?) {
                adapterClass!!.setData((contacts as MutableList<Contact>?)!!)

                for (i in contacts!!.indices) {
                    if (!contacts.get(i).isSelected()) {
                        temp.add(contacts.get(i))
                    }
                }
                // Log.i("try",temp.get(10).name)

            }
        })


    }


    private fun setAdapter() {
        val manager = LinearLayoutManager(this)
        recyclerView!!.setLayoutManager(manager)
        adapterClass = ContactAdapter()
        recyclerView!!.setAdapter(adapterClass)
    }


    override fun onItemChangeListener(position: Int, model: Contact) {
        try {

            val allOrders = creatGroupViewModel.getAllOrders()
            creatGroupViewModel.getAllOrders()!!.observe(this, object :
                Observer<MutableList<Contact>> {
                override fun onChanged(contacts: MutableList<Contact>?) {
                    contacts!!.set(position, model)

                }
            })

        } catch (e: Exception) {

        }

    }


    internal inner class CreatGroupViewModelFactory : ViewModelProvider.Factory {
        private var creatGroupActivity: CreatGroupActivity

        constructor(creatGroupActivity: CreatGroupActivity) {
            this.creatGroupActivity = creatGroupActivity

        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CreatGroupViewModel(
                creatGroupActivity
            ) as T
        }
    }
}
