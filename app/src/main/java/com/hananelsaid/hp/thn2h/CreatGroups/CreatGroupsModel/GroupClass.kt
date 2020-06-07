package com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel

import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact

 class GroupClass {
    lateinit var groupId: String
    lateinit var groupName: String
    lateinit var groupMembers: ArrayList<Contact>

    constructor( groupId: String,  groupName: String,  groupMembers: ArrayList<Contact>){
        this.groupId=groupId
        this.groupName=groupName
        this.groupMembers=groupMembers
    }


     constructor(){}

}

