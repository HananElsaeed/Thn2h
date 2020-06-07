package com.hananelsaid.hp.thn2h.ui.ChooseReciver.ChooseGroup

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hananelsaid.hp.thn2h.AddMessage.AddMessageActivity
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel.GroupClass
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import com.hananelsaid.hp.thn2h.contacts.ContactsViews.ContactAdapter
import com.hananelsaid.hp.thn2h.groups.GroupView.GroupsAdapter
import kotlinx.android.synthetic.main.group_child.view.*
import kotlinx.android.synthetic.main.groups_parent.view.*

class ChooseGroupAdapter: RecyclerView.Adapter<ChooseGroupAdapter.HolderClass>() {
    interface ChnageStatusListener {
        fun onItemChangeListener(position: Int, model: GroupClass)
    }
    private var ctx: Context? = null
    private var list: ArrayList<GroupClass>? = null
    internal var chnageStatusListener: ChooseGroupAdapter.ChnageStatusListener? = null





    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0;
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: HolderClass, position: Int) {
        val group: GroupClass = list!!.get(position)
        var numbersList:ArrayList<String> = ArrayList()
        var namesList:ArrayList<String> = ArrayList()

        if(group!=null) {

            holder.groupName.setText(group.groupName)
            holder.membersNumber.setText(group.groupMembers!!.size.toString())
            holder.members.setText("Members")

            holder.view.setOnClickListener(View.OnClickListener {

                val group = list!!.get(position)
                val groupMembers = group.groupMembers
                //var c :String = groupMembers[0].number
                var number :String
                var name :String
                for (i in groupMembers!!.indices)
                {
                    if (groupMembers.size>0)
                    {
                        val members = groupMembers.get(i) as HashMap<String,Any>
                         number = members.get("number").toString()
                        name = members.get("name").toString()

                       // Log.i("name",""+groupName)
                        numbersList.add(number)
                        namesList.add(name)
                    }
                }

                var backToAddMessage: Intent = Intent(ctx, AddMessageActivity::class.java)
                backToAddMessage.putStringArrayListExtra("numbers_list", numbersList)
                backToAddMessage.putStringArrayListExtra("names_list", namesList)
                Log.i("groupMembers",numbersList.toString())
                ctx!!.startActivity(backToAddMessage)


            })


        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {
        ctx = parent.getContext()
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(com.hananelsaid.hp.thn2h.R.layout.group_child, parent, false)
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
        val view: View

        var position:Int?=0

        @SuppressLint("ResourceAsColor")
        constructor(itemView: View) : super(itemView) {
           view=itemView
            groupName = itemView.tv_groupname2
            members = itemView.tv_members2
            membersNumber = itemView.tv_membersnumber2


            itemView.setBackgroundResource(com.hananelsaid.hp.thn2h.R.drawable.cardselectedbackground)




        }

    }
}