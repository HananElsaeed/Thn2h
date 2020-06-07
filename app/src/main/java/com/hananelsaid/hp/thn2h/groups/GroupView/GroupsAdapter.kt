package com.hananelsaid.hp.thn2h.groups.GroupView

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import kotlinx.android.synthetic.main.groups_parent.view.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase


class GroupsAdapter : RecyclerView.Adapter<GroupsAdapter.HolderClass>() {
    /*  interface ChnageStatusListener {
          fun onItemChangeListener(position: Int, model: Contact)
      }
      */

    private var ctx: Context? = null
    private var list: ArrayList<GroupClass>? = null
    // internal var chnageStatusListener: GroupsAdapter.ChnageStatusListener? = null


    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0;
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: HolderClass, position: Int) {
        val group: GroupClass = list!!.get(position)
        if (group != null) {

            holder.groupName.setText(group.groupName)
            holder.membersNumber.setText(group.groupMembers!!.size.toString())
            holder.members.setText("Members")
            holder.deleteImage.setOnClickListener {
                deleteGroup(position)
            }
        }

    }

    fun deleteGroup(position: Int) {
        val group: GroupClass = list!!.get(position)
        val groupId = group.groupId
        val ref = FirebaseDatabase.getInstance().reference
        val applesQuery = ref.child("groups")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .orderByChild("groupId").equalTo(groupId)


        applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (appleSnapshot in dataSnapshot.children) {
                    appleSnapshot.ref.removeValue()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("onCancelled", "hererererer" + databaseError.toException())
            }
        })

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {
        ctx = parent.getContext()
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(com.hananelsaid.hp.thn2h.R.layout.groups_parent, parent, false)
        return HolderClass(view)

    }

    fun setData(contactList: ArrayList<GroupClass>) {
        this.list = contactList
        notifyDataSetChanged()

    }


    inner class HolderClass : RecyclerView.ViewHolder {

        val groupName: TextView
        val membersNumber: TextView
        val members: TextView
        val deleteImage: ImageView
        val view: View

        var position: Int? = 0

        @SuppressLint("ResourceAsColor")
        constructor(itemView: View) : super(itemView) {
            view = itemView
            groupName = itemView.tv_groupname
            members = itemView.tv_members
            membersNumber = itemView.tv_membersnumber
            deleteImage = itemView.imageViewdelete

            itemView.setBackgroundResource(com.hananelsaid.hp.thn2h.R.drawable.cardselectedbackground)

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
}