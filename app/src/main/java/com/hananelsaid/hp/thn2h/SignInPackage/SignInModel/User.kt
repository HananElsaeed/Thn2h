package com.hananelsaid.hp.thn2h.SignInPackage.SignInModel

class User {
    var email:String
    get() {return email}
    var password :String
    get() {return password}
    var id:String = ""
    get() {return id}

    constructor(email:String,password:String){
        this.email=email
        this.password=password

    }
    fun anything(){

    }

}