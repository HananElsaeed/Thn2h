package com.hananelsaid.hp.thn2h.contacts.ContactsRepository

import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import com.hananelsaid.hp.thn2h.contacts.ContactsViewModel.ContactsViewModel
import java.util.*
import kotlin.collections.ArrayList

class ContactsRepository {

    var contactViewModel: ContactsViewModel


    constructor(contactViewModel: ContactsViewModel) {
        this.contactViewModel = contactViewModel
    }


    fun getContactsList(): List<Contact> {

        val contactList: MutableList<Contact> = ArrayList()
        val contacts = contactViewModel.passContext().contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,null)
        while (contacts!!.moveToNext()) {
            val name =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val obj = Contact()
            obj.name = name
            obj.number = number

            val photo_uri =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
            if (photo_uri != null) {
                obj.image = MediaStore.Images.Media.getBitmap(
                    contactViewModel.passContext().contentResolver,
                    Uri.parse(photo_uri)
                )
            }
            contactList.add(obj)
        }
        //contact_list.adapter = ContactAdapter(contactList,this)
        contacts.close()

        Collections.sort(contactList, object : Comparator<Contact> {
            override fun compare(o1: Contact, o2: Contact): Int {
                return o1.name.compareTo(o2.name)
            }
        })


        return contactList
    }

}




