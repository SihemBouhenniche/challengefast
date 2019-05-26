package com.example.challengefast.Models

import android.net.Uri
import com.google.firebase.database.ServerValue

class Post (var challenge:String, var tag:String, var description:String, var media:String) {
    lateinit var key:String
    var state:Int = -1
    var timeStamp = ServerValue.TIMESTAMP
}