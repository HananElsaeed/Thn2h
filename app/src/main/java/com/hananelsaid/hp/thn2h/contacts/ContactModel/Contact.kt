package com.hananelsaid.hp.thn2h.contacts.ContactModel

import android.graphics.Bitmap

class Contact {
    var name = ""
    var number = ""
    var image : Bitmap? = null
    private var isSelected: Boolean = false
     var IsWhatsappContact:Boolean=false

    fun setSelected(selected: Boolean) {
        isSelected = selected;
    }


    fun isSelected(): Boolean {
        return isSelected;
    }



}