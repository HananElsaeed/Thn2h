package com.hananelsaid.hp.thn2h.home

import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.hananelsaid.hp.thn2h.contacts.ContactsViews.ContactsFragment
import kotlinx.android.synthetic.main.fragment_home.*
import android.Manifest.permission_group.SMS
import android.content.Intent
import android.app.PendingIntent
import android.R
import android.telephony.SmsManager


class HomeFragment : Fragment() {

    private lateinit var mobileNumber: TextInputEditText
    lateinit var messageText: TextInputEditText
    lateinit var imopenContact: ImageView
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var message: String
    private lateinit var personNumber: String
    lateinit var contactsFragment: ContactsFragment


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(com.hananelsaid.hp.thn2h.R.layout.fragment_home, container, false)
        val floatbtn: FloatingActionButton = root.findViewById(com.hananelsaid.hp.thn2h.R.id.floatbtn)
        mobileNumber = root.findViewById(com.hananelsaid.hp.thn2h.R.id.etPersonNumber)
        messageText = root.findViewById(com.hananelsaid.hp.thn2h.R.id.etmessage)
        imopenContact = root.findViewById(com.hananelsaid.hp.thn2h.R.id.ivopenContacts)
        /* homeViewModel.text.observe(this, Observer {
             textView.text = it
         })*/
        contactsFragment = ContactsFragment()


        //floating actio button action
        floatbtn.setOnClickListener {
            personNumber = mobileNumber.text!!.toString().trim()
            message = messageText!!.text!!.toString().trim()
            checkInput()
            if (!personNumber.isEmpty() && !message.isEmpty()) {
                //send message on a certain time
                sendSMS(personNumber,message)
            }
        }
        return root
    }
    //---sends an SMS message to another device---
    private fun sendSMS(phoneNumber: String, message: String) {

        val pi = PendingIntent.getActivity(activity, 0, Intent(activity, SMS::class.java), 0
        )
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, pi, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Open contact
        ivopenContacts.setOnClickListener {
            setContactsFragment()
        }
    }


    override fun onStart() {
        super.onStart()
        val intent = activity!!.getIntent()
        val number = intent.getStringExtra("number")
        if (number != null) {
            etPersonNumber.setText(number.toString())
        }
    }

    fun setContactsFragment() {
        contactsFragment = ContactsFragment()
        fragmentManager!!.beginTransaction().replace(com.hananelsaid.hp.thn2h.R.id.nav_host_fragment, contactsFragment)
            .commit()
    }

    fun checkInput() {

        if (personNumber.isEmpty()) {
            mobileNumber.error = "Please enter the number"
            mobileNumber.requestFocus()
        }
        if (message.isEmpty()) {
            messageText.error = "Please enter the message"
            messageText.requestFocus()
        }

    }

}