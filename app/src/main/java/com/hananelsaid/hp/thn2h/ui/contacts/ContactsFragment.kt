package com.hananelsaid.hp.thn2h.ui.contacts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.hananelsaid.hp.thn2h.R
import com.hananelsaid.hp.thn2h.SignInPackage.SignInView.SignInActivity
import com.hananelsaid.hp.thn2h.SignInPackage.SignInViewModel.SignInViewModel

class ContactsFragment : Fragment() {

    private lateinit var contactsViewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactsViewModel =ViewModelProviders.of(this,ContactsViewModelFactory(this)).get(ContactsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        contactsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    fun passContext(): Context {
        return this!!.activity!!
    }

    internal inner class ContactsViewModelFactory : ViewModelProvider.Factory {
        private var contactFragment: ContactsFragment

        constructor(contactFragment: ContactsFragment) {
            this.contactFragment = contactFragment

        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ContactsViewModel(contactFragment) as T
        }
    }
}