package com.hananelsaid.hp.thn2h.PendingMessage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.hananelsaid.hp.thn2h.AddMessage.Model.MessageClass
class PendingmessageRepo {

    fun loadPendingMessages(ref: DatabaseReference): MutableLiveData<ArrayList<MessageClass>> {
        return FirebasPendingMessages.viewMessages(ref)
    }
}

object FirebasPendingMessages {
    var liveData = MutableLiveData<ArrayList<MessageClass>>()
    var messagesList = ArrayList<MessageClass>()

    fun viewMessages(ref: DatabaseReference): MutableLiveData<ArrayList<MessageClass>> {

        //var groupsList = ArrayList<MessageClass>()
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                messagesList.clear()
                var group: MessageClass
                for (messageSnapshot in dataSnapshot.children) {
                    val value = messageSnapshot.getValue(MessageClass::class.java)

                   // Log.i("FireUtilL", value.get("userid").toString())
                    if (value!!.getSmsStatus().equals("Pending")){

                        var message = MessageClass(value.getSmsId(),value.getsmsReceivers(),value.getSmsMsg(),value.getSmsDate()
                            ,value.getSmsTime(),value.getSmsStatus(),value.getSmsType()
                            ,value.getUserID(),value.getSmsCalender(),value.getSmsDelivered())
                        messagesList.add(message)

                    }
                }
                liveData.setValue(messagesList)
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
        return liveData

    }

}