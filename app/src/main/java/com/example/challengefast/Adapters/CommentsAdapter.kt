package com.example.challengefast.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.challengefast.Models.Comment
import com.example.challengefast.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.comment_layout.view.*


class CommentsAdapter : BaseAdapter {
    var context : Context?= null
    var modelList = ArrayList<Comment>()
    var postKEY = ""

    constructor(context : Context,modelList: ArrayList<Comment>){
        this.context = context
        this.modelList = modelList
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var mDatabase: FirebaseDatabase? = null

        //for retrive comment data
        var mPostReference: DatabaseReference? = null
        var mPostListener: ValueEventListener? = null
        //for retrive user data
        var mUserReference: DatabaseReference? = null
        var mUserListener: ValueEventListener? = null

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutItem = inflator.inflate(R.layout.comment_layout,null)

        mDatabase = FirebaseDatabase.getInstance()
        //display data inside layout :
        //put data
        val item = modelList.get(position)
        mPostReference = mDatabase!!.reference!!.child("Posts").child(postKEY).child("comments").child(item.key)
        mPostListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                layoutItem.comment_text.text = p0.child("comment").value.toString()
            }
        }
        mPostReference.addValueEventListener(mPostListener)

        //put user data infos
        mUserReference = mDatabase!!.reference!!.child("Users").child(item.userId)
        mUserListener = object  : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val last_name=snapshot . child ("lastName").value!! as String
                val first_name=snapshot . child ("firstName").value!! as String
                val full_name :String =last_name.plus(" ").plus(first_name)
                layoutItem.user_full_name_comment.text = full_name
                layoutItem.user_country_comment.text= snapshot . child ("country").value!! as String
            }
        }
        mUserReference!!.addValueEventListener(mUserListener!!)

        return layoutItem
    }

    override fun getItem(position: Int): Any {
        return modelList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return modelList.size
    }
}