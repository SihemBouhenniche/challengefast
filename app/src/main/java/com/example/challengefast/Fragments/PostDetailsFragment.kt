package com.example.challengefast.Fragments

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.Models.Post
import com.example.challengefast.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.post_details_fragment.*


class PostDetailsFragment : Fragment() {
    var position : Int = 0
    private lateinit var item : Post
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private var mUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = NavigationActivity.postsList.elementAt(position)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.post_details_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*mAuth = FirebaseAuth.getInstance()
        mUser=mAuth!!.currentUser*/

            fork_post_details.visibility=View.INVISIBLE


        //display data inside layout :
        //put data
        title_details.text = item.challenge.replace("\n","")
        tag_details.text = item.tag.replace("\n","")
        description_details.text = item.description.replace("\n","")
        stars_count_details.text = item.getStarsCount().toString() + " stars"

        //put user data infos
        mDatabase = FirebaseDatabase.getInstance()



        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        val mUserReference = mDatabaseReference!!.child(item.userId)
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val last_name=snapshot . child ("lastName").value!! as String
                val first_name=snapshot . child ("firstName").value!! as String
                val full_name :String =last_name.plus(" ").plus(first_name)
                user_full_name_details.text = full_name
                user_country_details.text= snapshot . child ("country").value!! as String
            }
        })




        //set the image
        var imageView : ImageView = media_details
        Glide.with(context)
            .load(Uri.parse(item.media))
            .into(imageView)


        //set star btn behavior
        star_btn_details.setOnClickListener {
            if(item.state == 1){
                star_btn_details.setBackgroundResource(R.drawable.ic_star_empty)
            }
            else{
                star_btn_details.setBackgroundResource(R.drawable.ic_star)
            }
            item.state = -1*item.state
        }

    }
}