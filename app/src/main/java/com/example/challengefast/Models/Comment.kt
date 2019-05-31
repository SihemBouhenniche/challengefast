package com.example.challengefast.Models

class Comment {
    var key : String = ""
    var userId : String = ""
    var comment : String = ""
    constructor(){}

    constructor(userId : String, comment : String){
        this.userId = userId
        this.comment = comment
    }
}