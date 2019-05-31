package com.example.challengefast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.Adapters.CommentsAdapter
import com.example.challengefast.Models.Comment
import com.example.challengefast.Models.Post
import com.example.challengefast.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.comment_layout.*
import kotlinx.android.synthetic.main.comments_fragment.*
import kotlinx.android.synthetic.main.post_details_fragment.*

class CommentsFragment : Fragment() {
    var position : Int = 0
    private lateinit var item : Post

    //vars for comments
    var commentsList : ListView?= null
    var commentsAdapter: CommentsAdapter?= null

    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = NavigationActivity.postsList.elementAt(position)
        commentsAdapter = CommentsAdapter(context!!, NavigationActivity.postsList.get(position).comments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.comments_fragment, container, false)
        Log.i("FRG","add fragment")
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //set the comments
        commentsList = activity!!.findViewById(R.id.comments_list_fragment)
        item = NavigationActivity.postsList.get(position)
        commentsAdapter!!.postKEY = item.key
        commentsList!!.adapter = commentsAdapter

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        val mUserUid = mAuth!!.currentUser!!.uid
        //set submit_comment_btn listner
        submit_comment_btn_frg.setOnClickListener{
            if(comment_text.text != ""){
                var comment = Comment(mUserUid,comment_field_frg.text.toString())
                val commentsRef = mDatabase!!.reference!!.child("Posts").child(item.key).child("comments").push()
                comment.key =  commentsRef.key.toString()
                commentsRef.setValue(comment).addOnSuccessListener { taskSnapshot ->
                    commentsAdapter!!.modelList.add(comment)
                    Toast.makeText(context, "You added a comment the challenge", Toast.LENGTH_SHORT).show()

                }.addOnFailureListener { error ->
                    Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}