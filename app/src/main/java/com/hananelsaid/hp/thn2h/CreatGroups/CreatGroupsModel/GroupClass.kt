package com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsModel

import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact

class GroupClass {
    lateinit var userid: String
    /*    get() {
            return userid
        }
        set(userid: String) {

            this.userid = userid
        }*/

    constructor(userid: String, groupName: String, groupMembers: ArrayList<Contact>) {
        this.userid = userid
        this.groupName = groupName
        this.groupMembers = groupMembers
    }

    constructor()
    {}
    lateinit var groupName: String
     /*   get() {
            return groupName
        }
        set(groupName: String) {
            this.groupName = groupName
        }*/
     lateinit var groupMembers: ArrayList<Contact>
     /*   get() {
            return groupMembers
        }
        set(groupMembers: ArrayList<Contact>) {
            this.groupMembers = groupMembers
        }*/


}