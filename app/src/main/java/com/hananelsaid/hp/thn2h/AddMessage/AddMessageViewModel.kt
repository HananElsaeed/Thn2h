package com.hananelsaid.hp.thn2h.AddMessage

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.telephony.SmsManager
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hananelsaid.hp.thn2h.AddMessage.Model.MessageClass
import com.hananelsaid.hp.thn2h.WhatsAppPackage.MessageService
import java.net.URLEncoder

class AddMessageViewModel : ViewModel {

    var addMessageRef: AddMessageActivity? = null
    var addMessageRepo: AddMessageRepo? = null
    private lateinit var mutableLiveData: MutableLiveData<List<MessageClass>>


    constructor(addMessageRef: AddMessageActivity) {
        //start whatsapp
        addMessageRef.startService(Intent(addMessageRef.getApplication(), MessageService::class.java))
        this.addMessageRef = addMessageRef
        addMessageRepo = AddMessageRepo(this)

        mutableLiveData = MutableLiveData()

    }

    fun uploadMessage(message: MessageClass) {
        Log.i("nnn", "يارب يشتغل")
        addMessageRepo!!.loadMessageToFirebase(message)
    }

    fun isAccessibilityOn(context: Context, clazz: Class<out AccessibilityService>): Boolean {
        var accessibilityEnabled = 0
        val service = context.packageName + "/" + clazz.canonicalName
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                context.applicationContext.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (ignored: Settings.SettingNotFoundException) {
        }
        val colonSplitter = TextUtils.SimpleStringSplitter(':')
        if (accessibilityEnabled == 1) {
            val settingValue = Settings.Secure.getString(
                context.applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (settingValue != null) {
                colonSplitter.setString(settingValue)
                while (colonSplitter.hasNext()) {
                    val accessibilityService = colonSplitter.next()
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun sendMessageNow(phoneNumber: ArrayList<String>, message: String ,sendVia :String,contactName:ArrayList<String>) {
        Log.i("hi", phoneNumber.size.toString())
        for (i in phoneNumber.indices) {
            var number = phoneNumber.get(i)
            var name:String = contactName.get(i)
            Log.i("sendornot", sendVia)

            if(sendVia.equals("SMS")){
           /*  val pi = PendingIntent.getActivity(
                 addMessageRef, 0, Intent(addMessageRef, SMS::class.java), 0)*/
             val sms = SmsManager.getDefault()
             sms.sendTextMessage(number, null, message, null, null)}

            if (sendVia.equals("WhatsApp"))
            {
                MessageService.sActive = true
                MessageService.sPhone = number
                MessageService.sContact = name
                MessageService.sMsg = message
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=" + number.replace("+", ""
                    ) + "&text=" + MessageService.sMsg)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addMessageRef!!.startActivity(intent)
                Thread.sleep(1000)
            }
        }
    }


    fun sendWhatsapp(numberText: String) {
        val mes = "test whatsapp"

        val packageManager = addMessageRef!!.getPackageManager()
        val i = Intent(Intent.ACTION_VIEW)
        try {
            val url = "https://api.whatsapp.com/send?text=" + numberText + URLEncoder.encode(mes, "UTF-8")
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            if (i.resolveActivity(packageManager) != null) {
                addMessageRef!!.startActivity(i)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Toast.makeText(addMessageRef, "WhatsApp sent", Toast.LENGTH_SHORT).show()
    }


}