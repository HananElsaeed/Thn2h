package com.hananelsaid.hp.thn2h.AddMessage

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hananelsaid.hp.thn2h.AddMessage.Model.MessageClass

class AddMessageRepo {

    var addMessageviewModel: AddMessageViewModel

    var myRef: DatabaseReference
    private var currentUser: FirebaseUser? = null


    constructor(addMessageviewModel: AddMessageViewModel) {
        this.addMessageviewModel = addMessageviewModel

        // Write a message to the database
        myRef = FirebaseDatabase.getInstance().getReference("messages")
        currentUser = FirebaseAuth.getInstance().currentUser
    }

    fun loadMessageToFirebase(message:MessageClass ) {
        if (message != null) {
            //val id = myRef.push().getKey()
           // if(id!=null)
                myRef.child(currentUser!!.getUid()).push().setValue(message)
              //  myRef.child(id).setValue(message)
        }


    }

}