package com.hananelsaid.hp.thn2h.CompletedMessages

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hananelsaid.hp.thn2h.AddMessage.Model.MessageClass

import com.hananelsaid.hp.thn2h.R

class CompletedMessagesFragment : Fragment() {

    private lateinit var completedViewModel: CompletedMessagesViewModel

    lateinit var recycler: RecyclerView
    var completedAdabter: CompletedMessageAdapter? = null
    lateinit var mdatabaseReference: DatabaseReference
    var pendingList: ArrayList<MessageClass> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        completedViewModel = ViewModelProviders.of(this).get(CompletedMessagesViewModel::class.java)
        val root = inflater.inflate(R.layout.completed_messages_fragment, container, false)

        recycler = root.findViewById(R.id.completedRecycler)
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

    }


    override fun onStart() {
        super.onStart()
        var timeNow: Long = System.currentTimeMillis()
        loadMessags(timeNow)

    }

    fun loadMessags(timeNow:Long) {
        completedViewModel.loadCompletedMessages(mdatabaseReference,timeNow).observe(this, Observer {

            completedAdabter!!.setData(it)
        })

    }
    private fun setAdapter() {

        val manager = LinearLayoutManager(activity)
        recycler!!.setLayoutManager(manager)
        completedAdabter = CompletedMessageAdapter()
        recycler!!.setAdapter(completedAdabter)


    }

}
