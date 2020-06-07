package com.hananelsaid.hp.thn2h

import android.provider.ContactsContract
import android.content.ContentResolver
import android.content.Context
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact


class WhatsAppContacts {
companion object {

    fun whatsAppList(context: Context): ArrayList<String> {

        //This class provides applications access to the content model.
        val cr = context.getContentResolver()

        //RowContacts for filter Account Types
        val contactCursor = cr.query(
            ContactsContract.RawContacts.CONTENT_URI,
            arrayOf(ContactsContract.RawContacts._ID, ContactsContract.RawContacts.CONTACT_ID),
            ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
            arrayOf("com.whatsapp"), null
        )

        //ArrayList for Store Whatsapp Contact
        val myWhatsappContacts: ArrayList<String> = ArrayList()

        if (contactCursor != null) {
            if (contactCursor!!.getCount() > 0) {
                if (contactCursor!!.moveToFirst()) {
                    do {
                        //whatsappContactId for get Number,Name,Id ect... from  ContactsContract.CommonDataKinds.Phone
                        val whatsappContactId = contactCursor!!.getString(
                            contactCursor!!.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID)
                        )

                        if (whatsappContactId != null) {
                            //Get Data from ContactsContract.CommonDataKinds.Phone of Specific CONTACT_ID
                            val whatsAppContactCursor = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                arrayOf(
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                                ),
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                arrayOf<String>(whatsappContactId), null
                            )

                            if (whatsAppContactCursor != null) {
                                whatsAppContactCursor!!.moveToFirst()
                                val id = whatsAppContactCursor!!.getString(
                                    whatsAppContactCursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                                )
                                val name = whatsAppContactCursor!!.getString(
                                    whatsAppContactCursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                                )
                                val number = whatsAppContactCursor!!.getString(
                                    whatsAppContactCursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                )

                                whatsAppContactCursor!!.close()

                               // var contact = Contact()
                                //contact.name = name
                                //contact.number = number
                                //Add Number to ArrayList
                                myWhatsappContacts.add(number)

                            }
                        }
                    } while (contactCursor!!.moveToNext())
                    contactCursor!!.close()
                }
            }
        }

        return myWhatsappContacts

    }
}

}