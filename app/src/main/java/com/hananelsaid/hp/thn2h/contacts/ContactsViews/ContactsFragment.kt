package com.hananelsaid.hp.thn2h.contacts.ContactsViews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hananelsaid.hp.thn2h.R
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import com.hananelsaid.hp.thn2h.contacts.ContactsViewModel.ContactsViewModel



class ContactsFragment : Fragment() ,ContactAdapter.ChnageStatusListener{


    private lateinit var contactsViewModel: ContactsViewModel
    private var adapterClass: ContactAdapter? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var searchView:SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contactsViewModel =ViewModelProviders.of(this,ContactsViewModelFactory(this)).get(
            ContactsViewModel::class.java)
        val root = inflater.inflate(com.hananelsaid.hp.thn2h.R.layout.fragment_contacts, container, false)
        recyclerView = root.findViewById(com.hananelsaid.hp.thn2h.R.id.contact_list)
        searchView=root.findViewById(com.hananelsaid.hp.thn2h.R.id.searchView)
       // recyclerView.textCh

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapterClass!!.getFilter().filter(newText)
                return false
            }
        })



        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setAdapter()
        contactsViewModel.getAllOrders()!!.observe(this, object : Observer<MutableList<Contact>> {
            override fun onChanged(contacts: MutableList<Contact>?) {
                adapterClass!!.setData((contacts as MutableList<Contact>?)!!)
            }
        })


    }

    private fun setAdapter() {
        val manager = LinearLayoutManager(activity)
        recyclerView!!.setLayoutManager(manager)
        adapterClass = ContactAdapter()
        recyclerView!!.setAdapter(adapterClass)
    }

    fun passContext(): Context {
        return this!!.activity!!
    }

    override fun onItemChangeListener(position: Int, model: Contact) {
        try {

            val allOrders = contactsViewModel.getAllOrders()
            contactsViewModel.getAllOrders()!!.observe(this, object : Observer<MutableList<Contact>> {
                override fun onChanged(contacts: MutableList<Contact>?) {
                    contacts!!.set(position, model)
                  //  adapterClass!!.setData((contacts as MutableList<Contact>?)!!)
                }
            })

        } catch (e: Exception) {

        }

    }

    internal inner class ContactsViewModelFactory : ViewModelProvider.Factory {
        private var contactFragment: ContactsFragment

        constructor(contactFragment: ContactsFragment) {
            this.contactFragment = contactFragment

        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ContactsViewModel(
                contactFragment
            ) as T
        }
    }


}