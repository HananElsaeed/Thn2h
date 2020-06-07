package com.hananelsaid.hp.thn2h.PendingMessage

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
import kotlinx.android.synthetic.main.pendingmessage_child.view.*

class PendingMessageAdapter : RecyclerView.Adapter<PendingMessageAdapter.HolderClass>() {
    /* interface ChnageStatusListener {
         fun onItemChangeListener(position: Int, model: Contact)
     }*/

    private var ctx: Context? = null
    private var list: MutableList<MessageClass>? = null
    private var mdatabaseReference = FirebaseDatabase.getInstance().getReference("messages")

    //internal var chnageStatusListener: ChnageStatusListener? = null

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0;
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {
        ctx = parent.getContext()
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.pendingmessage_child, parent, false)
        return HolderClass(view)

    }

    fun setData(messagetList: MutableList<MessageClass>) {
        this.list = messagetList
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
            reciversName = itemView.tv_reciverName
            time = itemView.tv_time
            date = itemView.tv_date
            ivMainphoto = itemView.iv_profile2
            messageText = itemView.tv_messagetxt
            imdelete = itemView.imageViewDeletemessage
            itemView.setBackgroundResource(R.drawable.cardselectedbackground)
            //this.view = itemView


        }

    }

}