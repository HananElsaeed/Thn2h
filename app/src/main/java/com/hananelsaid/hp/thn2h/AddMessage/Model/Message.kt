package com.hananelsaid.hp.thn2h.AddMessage.Model

import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact


class MessageClass {

  private lateinit var smsId: String
   // private lateinit var smsReceiverName: String
   // private lateinit var smsReceiverNumber: String
    private  var smsReceivers: ArrayList<String> =ArrayList()
    private lateinit var smsMsg: String
    private lateinit var smsDate: String
    private lateinit var smsTime: String
    private lateinit var smsStatus: String
    private lateinit var smsType: String
    private lateinit var userID: String
    private lateinit var smsDelivered:String
    private  var smsCalender: Long = 0

    constructor(){

    }

    constructor(smsId:String, smsReceivers: ArrayList<String>, smsMsg: String,
                smsDate: String, smsTime: String, smsStatus: String, smsType: String, userID: String,
                smsCalender: Long , smsDelivered:String) {
        this.smsId = smsId
        //this.smsReceiverName = smsReceiverName
        this.smsReceivers = smsReceivers
        this.smsMsg = smsMsg
        this.smsDate = smsDate
        this.smsTime = smsTime
        this.smsStatus = smsStatus
        this.smsType = smsType
        this.smsCalender = smsCalender
        this.userID = userID
        this.smsDelivered = smsDelivered
    }
    fun getSmsId(): String{ return smsId }

 //   fun getSmsReceiverName(): String{ return smsReceiverName }

    //fun getSmsReceiverNumber(): String{ return smsReceiverNumber }
    fun getsmsReceivers(): ArrayList<String>{ return smsReceivers }

    fun getSmsMsg(): String{ return smsMsg }

    fun getSmsDate(): String{ return smsDate }

    fun getSmsTime(): String{ return smsTime }

    fun getSmsStatus(): String{ return smsStatus }

    fun getSmsType(): String{ return smsType }

    fun getUserID(): String{ return userID }

    fun getSmsCalender(): Long{ return smsCalender }

    fun getSmsDelivered(): String{ return smsDelivered}
}