package com.hananelsaid.hp.thn2h

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.hananelsaid.hp.thn2h.AddMessage.AddMessageActivity
import kotlinx.android.synthetic.main.activity_option.*

class OptionActivity : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private lateinit var rbWhatsthenSMS: RadioButton
    private lateinit var smsOnly: RadioButton
    private lateinit var rbSMSthenWhats: RadioButton
    private lateinit var rbcustom: RadioButton
    private lateinit var openAddMessageActivity: Intent
    //shared preference to save choosen state
    lateinit var editor2: SharedPreferences.Editor
    val MY_PREFERN_NAME = "saveStatus"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        radioGroup = findViewById(R.id.radiogroup)

        //radio buttons objects
        rbWhatsthenSMS = findViewById(R.id.rbWhatsthenSMS)
        smsOnly = findViewById(R.id.rbSMS)
        rbSMSthenWhats = findViewById(R.id.rbSMSthenWhats)
        rbcustom = findViewById(R.id.rbcustom)


        openAddMessageActivity = Intent(this, AddMessageActivity::class.java)

        //after choose an option open add message activity to send message
        imageBtnSendingOptionDone.setOnClickListener {
            val checkedRadioButtonId = radioGroup.checkedRadioButtonId

            Log.i("idd", checkedRadioButtonId.toString())

            when (checkedRadioButtonId) {
                R.id.rbWhatsthenSMS ->putstateinShared("WhatsthenSMS")
                R.id.rbSMS ->  putstateinShared("SMS")
                R.id.rbSMSthenWhats -> putstateinShared("SMSthenWhats")
                R.id.rbcustom -> putstateinShared("custom")


            }
            if (checkedRadioButtonId != -1) {
                startActivity(openAddMessageActivity)
            }

        }

    }
    fun putstateinShared(state:String){

        //put data to shared prefrence
        editor2 = getSharedPreferences(MY_PREFERN_NAME,
            Context.MODE_PRIVATE
        ).edit()
        editor2.putString("state", state)
        editor2.apply()
    }

}
