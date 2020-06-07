package com.hananelsaid.hp.thn2h.PendingMessage

import android.telephony.SmsMessage
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.hananelsaid.hp.thn2h.AddMessage.Model.MessageClass

class PendingMessageViewModel : ViewModel{

    lateinit var pengimgMessageRepo: PendingmessageRepo

    private lateinit var mutableLiveData: MutableLiveData<ArrayList<MessageClass>>


    constructor(){
        pengimgMessageRepo = PendingmessageRepo()
        mutableLiveData = MutableLiveData()
    }

    fun loadPendingMessages(ref: DatabaseReference):MutableLiveData<ArrayList<MessageClass>>{
        return pengimgMessageRepo.loadPendingMessages(ref)
    }


}