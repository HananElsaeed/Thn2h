package com.hananelsaid.hp.thn2h.alarm

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.widget.Toast
import com.alialfayed.deersms.utils.NotificationHelper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MySMSReceiver /*: BroadcastReceiver()*/ {

   /* private lateinit var sentPendingIntent: PendingIntent
    private lateinit var deliveredPendingIntent: PendingIntent
    private lateinit var smsSentReceiver: BroadcastReceiver
    private lateinit var smsDeliveredReceiver: BroadcastReceiver

    private lateinit var smsId: String
    //private lateinit var smsReceiverName: String
    private lateinit var smsReceivers: ArrayList<String>
    private lateinit var smsMsg: String
    private lateinit var smsDate: String
    private lateinit var smsTime: String
    private lateinit var smsStatus: String
    private lateinit var smsType: String
    private lateinit var userID: String
    private var calendar: Long = 0
    private lateinit var smsDelivered :String

    lateinit var databaseReferenceMsg: DatabaseReference

    override fun onReceive(context: Context?, intent: Intent?) {

        databaseReferenceMsg = FirebaseDatabase.getInstance().getReference("messages")

        sentPendingIntent = PendingIntent.getBroadcast(context, 0, Intent("Message sent"), 0)
        deliveredPendingIntent =
            PendingIntent.getBroadcast(context, 0, Intent("message delivered"), 0)

        smsId = intent?.extras?.getString("SmsId")!!
        //smsReceiverName = intent.extras?.getString("SmsReceiverName")!!
        smsReceivers = intent.extras?.getString("SmsReceivers")!!
        smsMsg = intent.extras?.getString("SmsMsg")!!
        smsDate = intent.extras?.getString("SmsDate")!!
        smsTime = intent.extras?.getString("SmsTime")!!
        smsStatus = intent.extras?.getString("SmsStatus")!!
        smsType = intent.extras?.getString("SmsType")!!
        userID = intent.extras?.getString("UserID")!!
        calendar = intent.extras!!.getLong("calendar")
        smsDelivered = intent.extras?.getString("SmsDelivered")!!

        val smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
            smsReceiverNumber,
            null,
            smsMsg,
            sentPendingIntent,
            deliveredPendingIntent
        )


        smsSentReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show()

                        updateMsg("Completed" ,"Sent")
                        showNotification(context, "message sent")
                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                        Toast.makeText(context, "GENERIC FAILURE", Toast.LENGTH_SHORT).show()

                        updateMsg("GENERIC FAILURE" , "GENERIC FAILURE" )
                        showNotification(context, " GENERIC FAILURE")
                    }
                    SmsManager.RESULT_ERROR_NO_SERVICE -> {

                        Toast.makeText(context, "NO SERVICE", Toast.LENGTH_SHORT).show()

                        updateMsg("NO SERVICE" , "NO SERVICE")
                        showNotification(context, " NO SERVICE")
                    }
                    SmsManager.RESULT_ERROR_NULL_PDU -> {

                        Toast.makeText(context, "NULL PDU", Toast.LENGTH_SHORT).show()

                        updateMsg("NULL PDU" , "NO SERVICE")
                        showNotification(context, " NULL PDU")
                    }
                    SmsManager.RESULT_ERROR_RADIO_OFF -> {

                        Toast.makeText(context, "RADIO OFF", Toast.LENGTH_SHORT).show()

                        updateMsg("RADIO OFF" , "RADIO OFF")
                        showNotification(context, " RADIO OFF")
                    }
                }
            }
        }

        smsDeliveredReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(context, "message delivered", Toast.LENGTH_SHORT).show()
                        updateMsg("Completed" , "Delivered" )
                        showNotification(context, "message delivered")
                    }
                    Activity.RESULT_CANCELED -> {
                        Toast.makeText(context, "message not delivered", Toast.LENGTH_SHORT).show()
                        updateMsg("Not","Not Delivered")
                        showNotification(context, "message not delivered")
                    }
                }
            }
        }

        context?.applicationContext?.registerReceiver(smsSentReceiver, IntentFilter("message sent"))
        context?.applicationContext?.registerReceiver(
            smsDeliveredReceiver,
            IntentFilter("message delivered")
        )

    }

    private fun updateMsg(smsStatus: String , smsDelivered :String) {

        val message = SmsMessage.MessageClass(smsReceiverNumber, smsMsg, smsDate, smsTime,
            smsStatus, smsType, userID, calendar , smsDelivered)
        // update msg on fireBase
        databaseReferenceMsg.child(smsId).setValue(message)
    }

    private fun showNotification(context: Context?, msg: String) {
        val notificationHelper = NotificationHelper(
            context!!,
            smsReceivers,
            smsMsg,
            smsDate,
            smsTime,
            smsStatus,
            smsType,
            userID,
            msg
        )
        val nb = notificationHelper.getChannelNotification()
        notificationHelper.getManager().notify(smsId.hashCode(), nb.build())
    }

*/
}
