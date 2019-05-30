package com.example.challengefast.Adapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.Fragments.PostDetailsFragment
import com.example.challengefast.Models.Post
import com.example.challengefast.Models.Star
import com.example.challengefast.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.post_details_fragment.*
import kotlinx.android.synthetic.main.post_layout.view.*

class PostsAdapter : BaseAdapter {
    var context : Context?= null
    var modelList = ArrayList<Post>()



    constructor(context : Context,modelList: ArrayList<Post>){
        this.context = context
        this.modelList = modelList
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var mDatabase: FirebaseDatabase? = null
        var mAuth: FirebaseAuth? = null

        //for retrive post data
        var mPostReference: DatabaseReference? = null
        var mPostListener: ValueEventListener? = null
        //for retrive user data
        var mUserReference: DatabaseReference? = null
        var mUserListener: ValueEventListener? = null

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutItem = inflator.inflate(R.layout.post_layout,null)
        mDatabase = FirebaseDatabase.getInstance()
        //display data inside layout :
        //put data
        val item = NavigationActivity.postsList.get(position)
        Log.i("POSTKEY",item.key)

        mPostReference = mDatabase!!.reference!!.child("Posts").child(item.key)
        mPostListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //text data
                Log.i("POSTKEY",p0.child("key").toString())
                layoutItem.title_post.text = p0.child("challenge").value.toString()
                layoutItem.tag_post.text = p0.child("tag").value.toString()
                layoutItem.description_post.text = p0.child("description").value.toString()
                layoutItem.stars_count_post.text = p0.child("stars").children.count().toString() + " stars"

                //media data
                var imageView : ImageView = layoutItem.findViewById(R.id.media_post)
                Glide.with(context)
                    .load(Uri.parse(p0.child("media").value.toString()))
                    .into(imageView)
                imageView.setOnClickListener {
                    loadDetailsFragment(position)
                }

                mAuth = FirebaseAuth.getInstance()
                val mUserUid = mAuth!!.currentUser!!.uid
                Log.i("STAR",p0.child("stars").exists().toString())
                if (p0.child("stars").exists()){
                    var snapshotIterable: Iterable<DataSnapshot>  = p0.child("stars").children

                    var iterator: Iterator<DataSnapshot> = snapshotIterable.iterator()
                    while (iterator.hasNext()) {
                        val star = iterator.next()
                        //the user liked the post

                        if(star.child("userId").value.toString() == mUserUid){

                            layoutItem.star_btn.setBackgroundResource(R.drawable.ic_star)
                            layoutItem.star_btn.setOnClickListener{
                                deslikePost(layoutItem,star.child("key").value.toString())
                            }
                        }else{

                            layoutItem.star_btn.setOnClickListener{
                                likePost(layoutItem,mUserUid,star.child("key").value.toString())
                            }
                        }
                    }
                }else{
                    layoutItem.star_btn.setOnClickListener{
                        likePost(layoutItem,mUserUid,item.key)
                    }
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
                layoutItem.user_full_name_post.text = full_name
                layoutItem.user_country_post.text= snapshot . child ("country").value!! as String
            }
        }
        mUserReference!!.addValueEventListener(mUserListener!!)



        return layoutItem
    }

    private fun loadDetailsFragment(position: Int) {
        var fragment = PostDetailsFragment()
        fragment.position = position
        (context as NavigationActivity)!!.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

    private fun likePost(layoutItem:View,userId : String,key : String){
        var mDatabase: FirebaseDatabase? = null
        mDatabase = FirebaseDatabase.getInstance()

        layoutItem.star_btn.setBackgroundResource(R.drawable.ic_star)
        var star = Star(userId)
        val starsRef = mDatabase!!.reference!!.child("Posts").child(key).child("stars").push()
        star.key =  starsRef.key.toString()
        starsRef.setValue(star).addOnSuccessListener { taskSnapshot ->
            Toast.makeText(context, "You liked the challenge", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener { error ->
            layoutItem.star_btn.setBackgroundResource(R.drawable.ic_star_empty)
            Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun deslikePost(layoutItem: View,key : String){
        var mDatabase: FirebaseDatabase? = null
        mDatabase = FirebaseDatabase.getInstance()

        layoutItem.star_btn.setBackgroundResource(R.drawable.ic_star_empty)
        if(key != ""){
            val starsRef = mDatabase!!.reference!!.child("Posts").child(key).child("stars").child(key)
            starsRef.removeValue().addOnSuccessListener {
                Toast.makeText(context, "You disliked the challenge", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { error ->
                layoutItem.star_btn.setBackgroundResource(R.drawable.ic_star)
                Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
            }
        }
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