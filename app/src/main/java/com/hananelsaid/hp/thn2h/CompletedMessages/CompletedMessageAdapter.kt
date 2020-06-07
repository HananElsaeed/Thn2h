package com.hananelsaid.hp.thn2h.CompletedMessages

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hananelsaid.hp.thn2h.AddMessage.Model.MessageClass
import com.hananelsaid.hp.thn2h.R
import kotlinx.android.synthetic.main.completedmessage_child.view.*

class CompletedMessageAdapter : RecyclerView.Adapter<CompletedMessageAdapter.HolderClass>() {
    /* interface ChnageStatusListener {
         fun onItemChangeListener(position: Int, model: Contact)
     }*/

    private var ctx: Context? = null
    private var list: MutableList<MessageClass>? = null
    //internal var chnageStatusListener: ChnageStatusListener? = null

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0;
    }

    override fun onBindViewHolder(holder: HolderClass, position: Int) {
        val message: MessageClass = list!!.get(position)

        if (message != null) {
            Log.i("now", "now")
            holder.position = position

            holder.reciversName.setText(message.getsmsReceivers().toString())
            holder.time.setText(message.getSmsTime())
            holder.date.setText(message.getSmsDate())
            holder.messageText.setText(message.getSmsMsg())
            holder.imdelete.setOnClickListener {
                deleteMessage(position)
            }

            holder.ivMainphoto.setImageDrawable(ContextCompat.getDrawable(ctx!!, R.drawable.thn2h))


        }

        /*   holder.view.setOnClickListener(View.OnClickListener {
               val model1: MessageClass = list!!.get(position)
              *//* if (model1.isSelected()) {
                model1.setSelected(false)
            } else {
                model1.setSelected(true)
            }*//*
           *//* list!!.set(holder.position!!, model1)
            if (chnageStatusListener != null) {
                chnageStatusListener!!.onItemChangeListener(holder.position!!, model1)
            }*//*
            notifyItemChanged(holder.position!!)
        })*/
    }

    fun deleteMessage(position: Int) {
        val message: MessageClass = list!!.get(position)
        val smsId = message.getSmsId()
        val ref = FirebaseDatabase.getInstance().reference
        val applesQuery = ref.child("messages")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .orderByChild("smsId").equalTo(smsId)

        applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (appleSnapshot in dataSnapshot.children) {
                    appleSnapshot.ref.removeValue()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("onCancelled", "" + databaseError.toException())
            }
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {
        ctx = parent.getContext()
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.completedmessage_child, parent, false)
        return HolderClass(view)

    }

    fun setData(messagetList: MutableList<MessageClass>) {
        this.list = messagetList
        //fulllist = ArrayList(list!!)
        notifyDataSetChanged()

    }


    inner class HolderClass : RecyclerView.ViewHolder {

        val reciversName: TextView
        val time: TextView
        val date: TextView
        val messageText: TextView
        val ivMainphoto: ImageView
        val imdelete: ImageView
        //val view: View
        var position: Int? = 0


        constructor(itemView: View) : super(itemView) {
            reciversName = itemView.tv_reciverNamecompleted
            time = itemView.tv_timecompleted
            date = itemView.tv_datecompleted

            ivMainphoto = itemView.iv_profilecompleted
            messageText = itemView.tv_messagetxtcompleted
            imdelete = itemView.imageViewDeletecompleted
            itemView.setBackgroundResource(com.hananelsaid.hp.thn2h.R.drawable.cardselectedbackground)
            //this.view = itemView

        }

    }

}