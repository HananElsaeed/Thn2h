package com.hananelsaid.hp.thn2h.AddMessage

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hananelsaid.hp.thn2h.AddMessage.Model.MessageClass
import com.hananelsaid.hp.thn2h.HomePackage.HomeView.HomeActivity
import com.hananelsaid.hp.thn2h.ui.ChooseReciver.ChooseReciverActivity
import com.hananelsaid.hp.thn2h.R
import com.hananelsaid.hp.thn2h.WhatsAppPackage.MessageService
import com.hananelsaid.hp.thn2h.alarm.AlarmReciver
import kotlinx.android.synthetic.main.activity_add_message.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddMessageActivity : AppCompatActivity() {

    lateinit var myRef: DatabaseReference
    lateinit var id: String
    private lateinit var mobileNumber: TextInputEditText
    lateinit var messageText: TextInputEditText
    lateinit var imopenContact: ImageView
    lateinit var ibmessageExample: ImageButton
    lateinit var ibuploadMessage: ImageButton
    lateinit var etinitnumber: TextInputEditText

    val MY_PREFERN_NAME = "saveStatus"


    private lateinit var addMessageViewModel: AddMessageViewModel
    private lateinit var message: String
    private lateinit var personNumber: String
    private lateinit var initNumber: String
    private lateinit var date1: String
    private lateinit var time1: String
    private lateinit var numbersList: ArrayList<String>
    private lateinit var namesList: ArrayList<String>

    private lateinit var checkerButtonTime: RadioGroup
    private lateinit var checkerButtonSendVia: RadioGroup


    private var calender: DatePickerDialog.OnDateSetListener? = null
    private var timePickerDialog: TimePickerDialog? = null

    private var date: Date? = null
    private var myDateCheck: Date? = null
    private var mHour = 0
    private var mMin = 0
    private var hours1 = 0
    private var min1 = 0
    private var year1 = 0
    private var month1 = 0
    private var dayOfMonth1 = 0

    private lateinit var calendarAlarm: Calendar
    private lateinit var currentDate: String
    private lateinit var currentTime: String


    private var isSendNow: Boolean = false
    private var sendVia: String = " "
    var choosenState: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_message)


        myRef = FirebaseDatabase.getInstance().getReference("messages")
        id = myRef.push().getKey().toString()
        init()

        addMessageViewModel = ViewModelProviders.of(this, AddMessageViewModelFactory(this)).get(
            AddMessageViewModel::class.java
        )


        numbersList = ArrayList()
        numbersList.add("")
        //to add choose date and time
        radioBtnCustom_AddMessage.setOnClickListener {
            selectTime()
            selectDate()
        }

        calender = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            var month = month
            month += 1

            year1 = year
            month1 = month
            dayOfMonth1 = dayOfMonth

            val startDate = "$dayOfMonth/$month/$year"
            val time = SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().time)
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            try {
                date = format.parse(time)
                myDateCheck = format.parse(startDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            when {
                myDateCheck == null -> try {
                    myDateCheck = format.parse(time)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                myDateCheck!!.before(date) -> Toast.makeText(
                    this,
                    "Please, Enter a valid Date!", Toast.LENGTH_LONG
                ).show()
                else -> {
                    txtDate_AddMessage.setText(startDate)

                }
            }
        }

        /**
         * button do send Message
         */
        imageBtnSend_AddMessage.setOnClickListener {
            Log.i("issendnow", isSendNow.toString())
            checkInput()
            timesender()

            if (!personNumber.isEmpty() && !message.isEmpty()) {

                //if user select sms only and now
                if (isSendNow && choosenState.equals("SMS")) {
                    addNowMessageActivity(numbersList, currentDate, currentTime, sendVia)
                }
                if (!isSendNow && choosenState.equals("SMS")) {
                    addScheduleMessageActivity(numbersList, sendVia)
                    startActivity(Intent(this, HomeActivity::class.java))
                    // findNavController(R.id.nav_host_fragment);
                    this.finish()
                }


                //if user choose whatsapp then sms
                /* if (isSendNow && choosenState.equals("WhatsthenSMS")) {
                     var whatsapplist: ArrayList<String> = WhatsAppContacts.whatsAppList(this)
                     var whatsComingNumbers: ArrayList<String> = ArrayList()
                     var smsComingNumbers: ArrayList<String> = ArrayList()
                     for (i in numbersList.indices) {
                         if (whatsComingNumbers.contains(numbersList.get(i))) {
                             whatsComingNumbers.add(numbersList.get(i))
                         } else
                             smsComingNumbers.add(numbersList.get(i))

                     }
                     addNowMessageActivity(whatsComingNumbers,currentDate,currentTime,"WhatsApp")
                     addNowMessageActivity(smsComingNumbers,currentDate,currentTime,"SMS")

                 }

                 if (!isSendNow && choosenState.equals("WhatsthenSMS")) {
                     var whatsapplist: ArrayList<String> = WhatsAppContacts.whatsAppList(this)
                     var whatsComingNumbers: ArrayList<String> = ArrayList()
                     var smsComingNumbers: ArrayList<String> = ArrayList()
                     for (i in numbersList.indices) {
                         if (whatsComingNumbers.contains(numbersList.get(i))) {
                             whatsComingNumbers.add(numbersList.get(i))
                         } else
                             smsComingNumbers.add(numbersList.get(i))

                     }
                     addScheduleMessageActivity(whatsComingNumbers,"WhatsApp")
                     addScheduleMessageActivity(smsComingNumbers,"SMS")

                 }*/


                //if user select sms to people within same carier
                if (addMessageViewModel.isAccessibilityOn(this, MessageService::class.java)) {
                    if (isSendNow && choosenState.equals("SMSthenWhats") && !initNumber.isEmpty()) {
                        var samecarierlist: ArrayList<String> = ArrayList()
                        var othercarierlist: ArrayList<String> = ArrayList()
                        for (i in numbersList.indices) {
                            val number = numbersList.get(i)
                            if (number.startsWith(initNumber)) {
                                samecarierlist.add(number)
                            } else
                                othercarierlist.add(number)
                        }
                        addNowMessageActivity(samecarierlist, currentDate, currentTime, "SMS")

                        addNowMessageActivity(othercarierlist, currentDate, currentTime, "WhatsApp")
                    }


                    if (!isSendNow && choosenState.equals("SMSthenWhats") && !initNumber.isEmpty()) {
                        var samecarierlist: ArrayList<String> = ArrayList()
                        var othercarierlist: ArrayList<String> = ArrayList()
                        for (i in numbersList.indices) {
                            val number = numbersList.get(i)
                            if (number.startsWith(initNumber)) {
                                samecarierlist.add(number)
                            } else
                                othercarierlist.add(number)
                        }
                        addScheduleMessageActivity(samecarierlist, "SMS")

                        addScheduleMessageActivity(othercarierlist, "WhatsApp")

                    }
                } else startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))


                //if user choose custom
                if (choosenState.equals("custom") && addMessageViewModel.isAccessibilityOn(
                        this, MessageService::class.java
                    )
                ) {
                    //custom choose when he send the message
                    if (isSendNow) {
                        addNowMessageActivity(numbersList, currentDate, currentTime, sendVia)
                        openHome()
                    }

                    if (!isSendNow && sendVia.equals("SMS")) {
                        addScheduleMessageActivity(numbersList, sendVia)
                        openHome()
                    }

                    if (!isSendNow && sendVia.equals("WhatsApp")) {
                        addScheduleMessageActivity(numbersList, sendVia)
                        openHome()
                    }

                    /* if (isSendNow && sendVia.equals("WhatsApp")) {
                         addNowMessageActivity(numbersList, currentDate, currentTime, sendVia)
                     }*/

                }
                //startActivity(Intent(this, HomeActivity::class.java))
            }
        }

        //choose recivers
        imopenContact.setOnClickListener {
            startActivity(Intent(this, ChooseReciverActivity::class.java))
            this.finish()


        }

        /**
         * button get template Message
         */
        /*imageBtnAttach_AddMessage.setOnClickListener {
            addMessageViewModel.template()
        }*/
    }

    fun openHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        this.finish()
    }

    fun init() {
        calendarAlarm = Calendar.getInstance()
        currentDate = DateFormat.getDateInstance().format(calendarAlarm.time)
        currentTime = DateFormat.getTimeInstance().format(calendarAlarm.time)

        mobileNumber = findViewById(R.id.etPersonNumber)
        messageText = findViewById(R.id.etmessage)
        imopenContact = findViewById(R.id.ivopenContacts)
        ibmessageExample = findViewById(R.id.imageBtnAttach_AddMessage)
        ibuploadMessage = findViewById(R.id.imageBtnSend_AddMessage)
        etinitnumber = findViewById(R.id.etinitnumber)

        checkerButtonTime = findViewById(R.id.checkerButtonTime_AddMessage)
        checkerButtonSendVia = findViewById(R.id.checkerButtonVia_AddMessage)
    }


    fun checkInput() {

        personNumber = mobileNumber.text.toString().trim()
        message = messageText.text.toString().trim()
        initNumber = etinitnumber.text.toString().trim()
        date1 = txtDate_AddMessage.text.toString().trim()
        time1 = txtTime_AddMessage.text.toString().trim()
        if (personNumber.isEmpty()) {
            mobileNumber.error = "Please enter the number"
            mobileNumber.requestFocus()
        }
        if (message.isEmpty()) {
            messageText.error = "Please enter the message"
            messageText.requestFocus()
        }
        if (initNumber.isEmpty()) {
            etinitnumber.error = "Please enter your init number"
            etinitnumber.requestFocus()
        }


        /*   if (txtDate_AddMessage.text.toString().trim().isEmpty()) {
               Toast.makeText(this, "Enter a valid Date!", Toast.LENGTH_SHORT).show()
               return
           } else if (txtTime_AddMessage.text.toString().trim().isEmpty()) {
               Toast.makeText(this, "Enter a valid Time!", Toast.LENGTH_SHORT).show()
               return
           }*/

    }

    /**
     * Check Radio Button time & send via
     */
    private fun timesender() {
        //check radio button (now,custom)
        val checkedTimeRadioButtonId = checkerButtonTime.checkedRadioButtonId
        Log.i("idd", checkedTimeRadioButtonId.toString())
        when (checkedTimeRadioButtonId) {
            R.id.radioBtnNow_AddMessage -> {
                isSendNow = true
                Toast.makeText(this, "Message Send Now", Toast.LENGTH_SHORT).show()
            }
            R.id.radioBtnCustom_AddMessage -> {
                linearLayout_AddMessage.setVisibility(View.VISIBLE)
                isSendNow = false
                Toast.makeText(this, "Message Send Custom Time", Toast.LENGTH_SHORT).show()
            }
        }

        //check radio button (message ,whatsapp)

        val checkedSendViaRadioButtonId = checkerButtonSendVia.checkedRadioButtonId
        Log.i("idd", checkedTimeRadioButtonId.toString())
        when (checkedSendViaRadioButtonId) {
            R.id.radioBtnWhatsApp_AddMessage -> {
                sendVia = "WhatsApp"
                Toast.makeText(this, "Message Send Via WhatsApp", Toast.LENGTH_SHORT).show()
            }
            R.id.radioBtnSMS_AddMessage -> {
                linearLayout_AddMessage.setVisibility(View.VISIBLE)
                sendVia = "SMS"
                Toast.makeText(this, "Message Send Via SMS", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun selectDate() {

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(
            this,
            calender, year, month, day
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.show()
    }


    private fun selectTime() {
        val c = Calendar.getInstance()
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMin = c.get(Calendar.MINUTE)

        timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minutes ->
                val myCalInstance = Calendar.getInstance()
                val myRealCalender = Calendar.getInstance()

                if (myDateCheck == null) {
                    val timeStamp =
                        SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().time)
                    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    try {
                        myDateCheck = format.parse(timeStamp)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }

                myRealCalender.time = myDateCheck
                myRealCalender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myRealCalender.set(Calendar.MINUTE, minutes)

                if (myRealCalender.time.before(myCalInstance.time)) {
                    Toast.makeText(
                        this,
                        "Please, Enter a valid Time!", Toast.LENGTH_LONG
                    ).show()
                } else {
                    hours1 = hourOfDay
                    min1 = minutes
                    calendarAlarm = Calendar.getInstance()
                    calendarAlarm.set(Calendar.YEAR, year1)
                    calendarAlarm.set(Calendar.MONTH, month1 - 1)
                    calendarAlarm.set(Calendar.DAY_OF_MONTH, dayOfMonth1)
                    calendarAlarm.set(Calendar.HOUR_OF_DAY, hours1)
                    calendarAlarm.set(Calendar.MINUTE, min1)
                    calendarAlarm.set(Calendar.SECOND, 0)
                    if (hourOfDay < 10 && minutes >= 10) {
                        txtTime_AddMessage.setText("0$hourOfDay:$minutes")
                    } else if (hourOfDay < 10 && minutes < 10) {
                        txtTime_AddMessage.setText("0$hourOfDay:0$minutes")
                    } else if (hourOfDay >= 10 && minutes < 10) {
                        txtTime_AddMessage.setText("$hourOfDay:0$minutes")
                    } else if (hourOfDay >= 10 && minutes >= 10) {
                        txtTime_AddMessage.setText("$hourOfDay:$minutes")
                    }
                }
            }, mHour, mMin, false
        )
        timePickerDialog!!.show()
    }


    override fun onStart() {
        super.onStart()
        val prefs = getSharedPreferences(MY_PREFERN_NAME, Context.MODE_PRIVATE)
        var state: String? = prefs.getString("state", "No state defined")
        choosenState = state!!
        if (prefs != null) {
            if (state.equals("SMS") || state.equals("WhatsthenSMS") || state.equals("SMSthenWhats")) {
                textView10.setVisibility(View.GONE)
                checkerButtonVia_AddMessage.setVisibility(View.GONE)
            }
            if (state.equals("SMS") || state.equals("WhatsthenSMS") || state.equals("custom")) {
                etinitnumber.setVisibility(View.GONE)
            }

            if (state.equals("SMS"))
                sendVia = "SMS"
        }


        if (getIntent().getStringArrayListExtra("numbers_list") != null
            && getIntent().getStringArrayListExtra("names_list") != null
        ) {
            getNumberList()
            getNamesList()
        }
        if (numbersList.size > 0) {
            Log.i("hai", numbersList.get(0))
        }
        var allNumbers: StringBuffer = StringBuffer()
        if (numbersList.size > 0)
            for (i in numbersList.indices) {
                allNumbers.append(numbersList.get(i) + " , ")
            }
        mobileNumber.setText(allNumbers)
    }

    fun getNumberList() {

        numbersList = getIntent().getStringArrayListExtra("numbers_list")

    }

    fun getNamesList() {

        namesList = getIntent().getStringArrayListExtra("names_list")

    }

    fun setMessageAlarm(numberList: ArrayList<String>, sendVia: String,message:String) {

        var intent = Intent(this, AlarmReciver::class.java)
        intent.setAction(calendarAlarm.timeInMillis.toString())
       // intent.setAction(Long.toString(System.currentTimeMillis()))
        intent.putExtra("msgText", message)
        intent.putExtra("sendVia", sendVia)
        intent.putStringArrayListExtra("numbers", numberList)
        Log.i("sendornot", sendVia)

        var pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0, intent, 0)
        var am: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am!!.setExact(AlarmManager.RTC_WAKEUP, calendarAlarm.timeInMillis, pendingIntent)
    }


    fun addScheduleMessageActivity(numbersListt: ArrayList<String>, sendVia: String) {

        var messageClass = MessageClass(
            id, numbersList, message, date1, time1, "Pending", sendVia,
            FirebaseAuth.getInstance().currentUser!!.uid, calendarAlarm.timeInMillis, "Sent"
        )
        addMessageViewModel.uploadMessage(messageClass)
        Toast.makeText(this, "SMS Send at " + date + time1, Toast.LENGTH_LONG).show()
        setMessageAlarm(numbersListt, sendVia,message)


    }

    fun addNowMessageActivity(
        numbersListt: ArrayList<String>,
        date: String,
        time: String,
        sendVia: String
    ) {
        var messageClass = MessageClass(
            id, numbersListt, message, date, time, "Completed", sendVia,
            FirebaseAuth.getInstance().currentUser!!.uid, calendarAlarm.timeInMillis, "Sent"
        )

        addMessageViewModel.uploadMessage(messageClass)
        addMessageViewModel.sendMessageNow(numbersListt, message, sendVia, namesList)


    }


    internal inner class AddMessageViewModelFactory : ViewModelProvider.Factory {
        private var addMessageActivity: AddMessageActivity

        constructor(addMessageActivity: AddMessageActivity) {
            this.addMessageActivity = addMessageActivity

        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddMessageViewModel(
                addMessageActivity
            ) as T
        }
    }

}
