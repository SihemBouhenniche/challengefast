package com.example.challengefast.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.Adapters.CommentsAdapter
import com.example.challengefast.Models.Comment
import com.example.challengefast.Models.Post
import com.example.challengefast.Models.Star

import com.example.challengefast.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.comment_layout.*
import kotlinx.android.synthetic.main.post_details_fragment.*



class PostDetailsFragment : Fragment() {
    var position : Int = 0
    private lateinit var item : Post
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    //for retrive post data
    private var mPostReference: DatabaseReference? = null
    private var mPostListener: ValueEventListener? = null
    //for retrive user data
    private var mUserReference: DatabaseReference? = null
    private var mUserListener: ValueEventListener? = null

    //vars for comments
    var commentsList : ListView ?= null
    var commentsAdapter:CommentsAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = NavigationActivity.postsList.elementAt(position)
        commentsAdapter = CommentsAdapter(context!!,NavigationActivity.postsList.get(position).comments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.post_details_fragment, container, false)
        Log.i("FRG","add fragment")
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance()
        //display data inside layout :
        //put data
        mPostReference = mDatabase!!.reference!!.child("Posts").child(item.key)
        mPostListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                //text data
                title_details.text = p0.child("challenge").value.toString()
                tag_details.text = p0.child("tag").value.toString()
                description_details.text = p0.child("description").value.toString()
                stars_count_details.text = p0.child("stars").children.count().toString() + " stars"
                comments_count_details.text = p0.child("comments").children.count().toString() + " comment"
                //media data
                var imageView : ImageView = media_details
                Glide.with(context)
                    .load(Uri.parse(p0.child("media").value.toString()))
                    .into(imageView)

                mAuth = FirebaseAuth.getInstance()
                val mUserUid = mAuth!!.currentUser!!.uid
                Log.i("STAR",p0.child("stars").exists().toString())
                if (p0.child("stars").exists()){
                    var snapshotIterable: Iterable<DataSnapshot>  = p0.child("stars").children
                    Log.i("STAR",p0.child("stars").childrenCount.toString())
                    var iterator: Iterator<DataSnapshot> = snapshotIterable.iterator()
                    while (iterator.hasNext()) {
                        val star = iterator.next()
                        //the user liked the post
                        Log.i("STAR",star.child("userId").toString() + mUserUid )
                        if(star.child("userId").value.toString() == mUserUid){
                            Log.i("STAR","user liked post" )
                            star_btn_details.setBackgroundResource(R.drawable.ic_star)
                            star_btn_details.setOnClickListener{
                                deslikePost(star.child("key").value.toString())
                            }
                        }else{
                            Log.i("STAR","user disliked post" )

                            star_btn_details.setOnClickListener{
                                likePost(mUserUid)
                            }
                        }
                    }
                }else{
                    Log.i("STAR","user liked post" )
                    star_btn_details.setOnClickListener{
                        likePost(mUserUid)
                    }
                }

                //set submit_comment_btn listner
                submit_comment_btn.setOnClickListener{
                    if(comment_text.text != ""){
                        var comment = Comment(mUserUid,comment_field.text.toString())
                        val commentsRef = mDatabase!!.reference!!.child("Posts").child(item.key).child("comments").push()
                        comment.key =  commentsRef.key.toString()
                        commentsRef.setValue(comment).addOnSuccessListener { taskSnapshot ->
                            Toast.makeText(context, "You added a comment the challenge", Toast.LENGTH_SHORT).show()

                        }.addOnFailureListener { error ->
                            Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                //set on load all comments
                comment_btn_details.setOnClickListener {
                    loadNextFragment(position)
                }
                load_comment_fragment_btn.setOnClickListener {
                    loadNextFragment(position)
                }

                //set on share btn
                share_btn_details.setOnClickListener{
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, p0.child("challenge").value.toString())
                        type = "text/plain"
                    }
                    context!!.startActivity(Intent.createChooser(sendIntent, "Share challenge in : "))
                }

            }
        }

        //add the listener
        mPostReference!!.addValueEventListener(mPostListener!!)

        //put user data infos
        mUserReference = mDatabase!!.reference!!.child("Users").child(item.userId)
        mUserListener = object  : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val last_name=snapshot . child ("lastName").value!! as String
                val first_name=snapshot . child ("firstName").value!! as String
                val full_name :String =last_name.plus(" ").plus(first_name)
                user_full_name_details.text = full_name
                user_country_details.text= snapshot . child ("country").value!! as String
            }
        }
        mUserReference!!.addValueEventListener(mUserListener!!)


        //set the comments
        commentsList = activity!!.findViewById(R.id.comments_list)

        commentsAdapter!!.postKEY = item.key
        commentsList!!.adapter = commentsAdapter



    }

    private fun likePost(userId : String){
        star_btn_details.setBackgroundResource(R.drawable.ic_star)
        var star = Star(userId)
        val starsRef = mDatabase!!.reference!!.child("Posts").child(item.key).child("stars").push()
        star.key =  starsRef.key.toString()
        starsRef.setValue(star).addOnSuccessListener { taskSnapshot ->
            Toast.makeText(context, "You liked the challenge", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener { error ->
            star_btn_details.setBackgroundResource(R.drawable.ic_star_empty)
            Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun deslikePost(key : String){
        star_btn_details.setBackgroundResource(R.drawable.ic_star_empty)
        if(key != ""){
            val starsRef = mDatabase!!.reference!!.child("Posts").child(item.key).child("stars").child(key)
            starsRef.removeValue().addOnSuccessListener {
                Toast.makeText(context, "You disliked the challenge", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { error ->
                star_btn_details.setBackgroundResource(R.drawable.ic_star)
                Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mPostReference!!.removeEventListener(mPostListener!!)
        mUserReference!!.removeEventListener(mUserListener!!)
    }

    private fun loadNextFragment(position: Int){
        var fragment = CommentsFragment()
        fragment.position = position
        activity!!.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

}