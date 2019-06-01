package com.example.challengefast.Models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ServerValue


@IgnoreExtraProperties

class Post {
    var challenge:String =""
    var tag:String =""
    var description:String = ""
    var media:String = ""
    lateinit var key:String
    var userId: String = ""
    var state:Int = -1
    var timeStamp = ServerValue.TIMESTAMP
    var stars = ArrayList<Star>()
    var comments = ArrayList<Comment>()

    constructor()  {

    }
    constructor(challenge:String, tag:String, description:String, media:String,key:String,userUid : String){
        this.challenge = challenge
        this.tag = tag
        this.description = description
        this.media = media
        this.key = key
        this.userId = userUid
    }

    constructor(title: String, tag: String, description: String, media: String,userUid : String){
        this.challenge = title
        this.tag = tag
        this.description = description
        this.media = media
        this.userId = userUid
    }
}