package com.hananelsaid.hp.thn2h.ui.ChooseReciver.ChooseGroup

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.R
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact

/**
 * A placeholder fragment containing a simple view.
 */
class ChooseGroupFragment : Fragment(), ChooseGroupAdapter.ChnageStatusListener {
companion object {
    fun newInstance()=ChooseGroupFragment()
}

    private lateinit var pageViewModel: ChooseGroupViewModel
    var groupsList: ArrayList<GroupClass> = ArrayList()
    lateinit var databaseGroups: DatabaseReference
    private var adapterClass: ChooseGroupAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        recyclerView = root.findViewById(R.id.recyclerdisplayGroups)
        databaseGroups = FirebaseDatabase.getInstance().getReference("groups")
            .child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())

        setAdapter()

        return root
    }

    private fun setAdapter() {
        val manager = LinearLayoutManager(activity)
        recyclerView!!.setLayoutManager(manager)
        adapterClass = ChooseGroupAdapter()
        recyclerView!!.setAdapter(adapterClass)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(ChooseGroupViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()
        loadGroups()
    }

    fun loadGroups() {
        pageViewModel.loadGroups(databaseGroups).observe(this, Observer {
            adapterClass!!.setData(it)
        })

    }

    override fun onItemChangeListener(position: Int, model: GroupClass) {
        groupsList.set(position, model)

    }

}