package com.hananelsaid.hp.thn2h.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import android.util.Log
import com.hananelsaid.hp.thn2h.WhatsAppPackage.MessageService

class AlarmReciver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, i: Intent?) {
        val message = i!!.getStringExtra("msgText")
        val SSms = i.getStringArrayListExtra("numbers")
        val sendVia: String = i.getStringExtra("sendVia")
        Log.i("sendornot", sendVia)

        if (SSms.size > 0) {
            for (i in SSms.indices) {
                var number: String = SSms.get(i).toString()
                if (sendVia.equals("WhatsApp")) {
                    MessageService.sActive = true
                    MessageService.sPhone = number
                    // MessageService.sContact = smsReceiverName
                    MessageService.sMsg = message
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://api.whatsapp.com/send?phone=" + number.replace("+", ""
                        ) + "&text=" + MessageService.sMsg)
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    p0?.startActivity(intent)
                }
                if (sendVia.equals("SMS")) {
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(number, null, message, null, null)
                }
            }
        }
    }
}