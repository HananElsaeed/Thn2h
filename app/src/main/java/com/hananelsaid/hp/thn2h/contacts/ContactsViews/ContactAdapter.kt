package com.hananelsaid.hp.thn2h.contacts.ContactsViews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hananelsaid.hp.thn2h.HomePackage.HomeView.HomeActivity
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import kotlinx.android.synthetic.main.contact_child.view.*
import android.R
import android.widget.Filter
import android.widget.Filterable
import android.R.attr.name
import android.widget.Toast
import android.R.attr.name
import android.annotation.SuppressLint
import android.graphics.Color
import android.R.attr.name
import android.R.attr.name


class ContactAdapter : RecyclerView.Adapter<ContactAdapter.HolderClass>(), Filterable {
    interface ChnageStatusListener {
        fun onItemChangeListener(position: Int, model: Contact)
    }

    private var ctx: Context? = null
    private var list: MutableList<Contact>? = null
    private var fulllist: List<Contact>? = null
    internal var chnageStatusListener: ChnageStatusListener? = null

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0;
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: HolderClass, position: Int) {
        val contact: Contact = list!!.get(position)
        if(contact!=null) {
            holder.position = position
            holder.contactName.setText(contact.name)
            holder.contactNumber.setText(contact.number)
            if (contact.image != null)
                holder.contactImage.setImageBitmap(contact.image)
            else
                holder.contactImage.setImageDrawable(
                    ContextCompat.getDrawable(ctx!!, com.hananelsaid.hp.thn2h.R.drawable.thn2h)
                )
            if (contact.isSelected()) {
                holder.view.setBackgroundResource(com.hananelsaid.hp.thn2h.R.drawable.btnexittext)
            } else {
                holder.view.setBackgroundResource(com.hananelsaid.hp.thn2h.R.drawable.cardselectedbackground)
            }


        }
        holder.view.setOnClickListener(View.OnClickListener {
            val model1:Contact = list!!.get(position)
            if (model1.isSelected()) {
                model1.setSelected(false)
            } else {
                model1.setSelected(true)
            }
            list!!.set(holder.position!!, model1)
            if (chnageStatusListener != null) {
                chnageStatusListener!!.onItemChangeListener(holder.position!!, model1)
            }
            notifyItemChanged(holder.position!!)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {
        ctx = parent.getContext()
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(com.hananelsaid.hp.thn2h.R.layout.contact_child, parent, false)
        return HolderClass(view)

    }

    fun setData(contactList: MutableList<Contact>) {
        this.list = contactList
        fulllist = ArrayList(list!!)
        notifyDataSetChanged()

    }


    inner class HolderClass : RecyclerView.ViewHolder {

        val contactName: TextView
        val contactNumber: TextView
        val contactImage: ImageView
        val view: View
        //val ivMainphoto: ImageView
        var position:Int?=0


        @SuppressLint("ResourceAsColor")
        constructor(itemView: View) : super(itemView) {
            contactName = itemView.tv_name
            contactNumber = itemView.tv_number
            contactImage = itemView.iv_profile
            //ivMainphoto = itemView.ivmainphoto
            this.view = itemView
            /*
                itemView.setOnLongClickListener {
                    val p = layoutPosition
                    val contact = list!!.get(p)
                    true
                }*/
/*

            itemView.setOnClickListener {
                val p = layoutPosition

                val contact = list!!.get(p)
                contact.setSelected(!contact.isSelected())
                if (contact.isSelected())
                itemView.setBackgroundColor( Color.GRAY )
                else    itemView.setBackgroundColor( Color.WHITE )

                *//*    var backToHome: Intent = Intent(ctx, HomeActivity::class.java)
                    backToHome.putExtra("number", contact.number)
                    ctx!!.startActivity(backToHome)*//*
            }*/


        }

    }

    //search button
    override fun getFilter(): Filter {
        return contactsFilter
    }

    private val contactsFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val filteredList: ArrayList<Contact> = ArrayList()

            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(fulllist!!)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }

                for (item in fulllist!!) {
                    if (item.name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }

            val results = Filter.FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            list!!.clear()
            list!!.addAll(results.values as Collection<Contact>)
            notifyDataSetChanged()
        }
    }


}