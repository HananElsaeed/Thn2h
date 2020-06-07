package com.hananelsaid.hp.thn2h.PendingMessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.hananelsaid.hp.thn2h.contacts.ContactsViews.ContactsFragment
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.hananelsaid.hp.thn2h.AddMessage.AddMessageActivity
import com.hananelsaid.hp.thn2h.AddMessage.Model.MessageClass
import com.hananelsaid.hp.thn2h.OptionActivity
import android.R.attr.data
import com.google.firebase.auth.FirebaseAuth


class PendingMessageFragment : Fragment() {

    private lateinit var pendingViewModel: PendingMessageViewModel

    lateinit var contactsFragment: ContactsFragment
    lateinit var floatbtn: FloatingActionButton
    lateinit var recycler: RecyclerView
    var pendingAdabter: PendingMessageAdapter? = null
    lateinit var mdatabaseReference: DatabaseReference
    var pendingList: ArrayList<MessageClass> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pendingViewModel = ViewModelProviders.of(this).get(PendingMessageViewModel::class.java)
        val root =
            inflater.inflate(com.hananelsaid.hp.thn2h.R.layout.fragment_home, container, false)
        floatbtn = root.findViewById(com.hananelsaid.hp.thn2h.R.id.floatbtn)
        recycler = root.findViewById(com.hananelsaid.hp.thn2h.R.id.recyclerViewPending)
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("messages")
            .child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())

        setAdapter()
        //mdatabaseReference.keepSynced(true)


        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Open addmessage
        //floating actio button action
        floatbtn.setOnClickListener {
            var openAddMesssage = Intent(this.activity, OptionActivity::class.java)
            activity!!.startActivity(openAddMesssage)
            //activity!!.finish()
        }

    }


    override fun onStart() {
        super.onStart()
        loadMessags()
    }

    fun loadMessags() {
        pendingViewModel.loadPendingMessages(mdatabaseReference).observe(this, Observer {

            pendingAdabter!!.setData(it)
        })

    }

    private fun setAdapter() {

        val manager = LinearLayoutManager(activity)
        recycler!!.setLayoutManager(manager)
        pendingAdabter = PendingMessageAdapter()
        recycler!!.setAdapter(pendingAdabter)


    }


}