package com.hananelsaid.hp.thn2h.CompletedMessages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.hananelsaid.hp.thn2h.AddMessage.Model.MessageClass

class CompletedMessagesViewModel : ViewModel {
    lateinit var completedMessageRepo: CompletedMessagesRepo

    private lateinit var mutableLiveData: MutableLiveData<ArrayList<MessageClass>>


    constructor(){
        completedMessageRepo = CompletedMessagesRepo()
        mutableLiveData = MutableLiveData()
    }

    fun loadCompletedMessages(ref: DatabaseReference,timeNow:Long):MutableLiveData<ArrayList<MessageClass>>{
        return completedMessageRepo.loadCompletedMessages(ref,timeNow)
    }
}
