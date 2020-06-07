package com.hananelsaid.hp.thn2h.ui.ChooseReciver.ChooseContacts

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText


import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import com.hananelsaid.hp.thn2h.contacts.ContactsViews.ContactAdapter
import android.content.Intent

import android.widget.Toast
import com.hananelsaid.hp.thn2h.AddMessage.AddMessageActivity
import android.R.attr.data
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager


class ChooseContactsFragment : Fragment() {


    companion object {
        fun newInstance() = ChooseContactsFragment()
    }

    private lateinit var viewModel: ChooseContactsViewModel
    private var adapterClass: ContactAdapter? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var searchView: SearchView
    private lateinit var floatbtnchoosen: com.google.android.material.floatingactionbutton.FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(com.hananelsaid.hp.thn2h.R.layout.choose_contacts_fragment, container, false)
        recyclerView = view.findViewById(com.hananelsaid.hp.thn2h.R.id.contact_list3)
        searchView = view.findViewById(com.hananelsaid.hp.thn2h.R.id.searchView3)


        floatbtnchoosen = view.findViewById(com.hananelsaid.hp.thn2h.R.id.fabChoosen)

        return view
    }
    override fun onStart() {
        super.onStart()
        var temp = java.util.ArrayList<Contact>()

        setAdapter()
        viewModel.getAllOrders()!!.observe(this, object : Observer<MutableList<Contact>> {

            override fun onChanged(contacts: MutableList<Contact>?) {
                adapterClass!!.setData((contacts as MutableList<Contact>?)!!)

                for (i in contacts!!.indices) {
                    if (!contacts.get(i).isSelected()) {
                        temp.add(contacts.get(i))
                    }
                }
                // Log.i("try",temp.get(10).name)

            }
        })


    }
    private fun setAdapter() {
        val manager = LinearLayoutManager(activity)
        recyclerView!!.setLayoutManager(manager)
        adapterClass = ContactAdapter()
        recyclerView!!.setAdapter(adapterClass)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,ChooseContactsViewModelFactory(this)).get(ChooseContactsViewModel::class.java)



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapterClass!!.getFilter().filter(newText)
                return false
            }
        })
        floatbtnchoosen.setOnClickListener {
        //get Choosen contacts and send it to add message activity
        val temp = ArrayList<String>()
        val names =ArrayList<String>()
        viewModel.getAllOrders()!!.observe(
            this,
            object : Observer<MutableList<Contact>> {
                override fun onChanged(contacts: MutableList<Contact>?) {
                    for (i in contacts!!.indices) {
                        if (contacts.get(i).isSelected()) {
                            temp.add(contacts.get(i).number)
                            names.add(contacts.get(i).name)
                        }
                    }
                }
            })

        //Log.i("try", ""+temp.get(0).name)
        if (temp.size > 0) {
            val intent = Intent(this.activity, AddMessageActivity::class.java)
            intent.putStringArrayListExtra("numbers_list", temp)
            intent.putStringArrayListExtra("names_list", temp)
            startActivity(intent)
            activity!!.finish()
        } else
            Toast.makeText(activity, "Please choose one contact at least", Toast.LENGTH_LONG).show()

    }

    }

    internal inner class ChooseContactsViewModelFactory : ViewModelProvider.Factory {
        private var chooseContactsFragment: ChooseContactsFragment

        constructor(chooseContactsFragment: ChooseContactsFragment) {
            this.chooseContactsFragment = chooseContactsFragment

        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChooseContactsViewModel(
                chooseContactsFragment
            ) as T
        }
    }

}
