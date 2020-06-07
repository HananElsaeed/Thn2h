package com.hananelsaid.hp.thn2h.alarm

import android.Manifest.permission_group.SMS
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import com.hananelsaid.hp.thn2h.WhatsAppPackage.MessageService
import java.security.Provider

class AlarmService : Service() {


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(i: Intent, flags: Int, startId: Int): Int {
        val message = i.getStringExtra("msgText")
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
                    startActivity(intent)
                }
                if (sendVia.equals("SMS")) {
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(number, null, message, null, null)
                }
            }
        }
        return Service.START_STICKY
    }
}