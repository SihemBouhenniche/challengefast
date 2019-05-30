package com.example.challengefast.Adapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.Fragments.PostDetailsFragment
import com.example.challengefast.Models.Post
import com.example.challengefast.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.post_layout.view.*

class PostsAdapter : BaseAdapter {
    var context : Context?= null
    var modelList = ArrayList<Post>()
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null


    constructor(context : Context,modelList: ArrayList<Post>){
        this.context = context
        this.modelList = modelList
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutItem = inflator.inflate(R.layout.post_layout,null)
        val item = modelList.get(position)

        //put data
        layoutItem.title_post.text = item.challenge.replace("\n","")
        layoutItem.tag_post.text = item.tag.replace("\n","")
        layoutItem.description_post.text = item.description.replace("\n","")

        //put user data infos
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        val mUserReference = mDatabaseReference!!.child(item.userId)
        Log.i("UID",item.userId)
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val last_name=snapshot . child ("lastName").value!! as String
                val first_name=snapshot . child ("firstName").value!! as String
                val full_name :String =last_name.plus(" ").plus(first_name)
                layoutItem.user_full_name_post.text = full_name
                layoutItem.user_country_post.text= snapshot . child ("country").value!! as String
            }
        })




        //set the image
        var imageView : ImageView = layoutItem.media_post
        //set on click listenr on the image
        imageView.setOnClickListener {
            loadDetailsFragment(position)
        }
        Glide.with(context)
            .load(Uri.parse(item.media))
            .into(imageView)


        //set star btn behavior
        layoutItem.star_btn.setOnClickListener {
            if(item.state == 1){
                layoutItem.star_btn.setBackgroundResource(R.drawable.ic_star_empty)
            }
            else{
                layoutItem.star_btn.setBackgroundResource(R.drawable.ic_star)
            }
            item.state = -1*item.state
        }
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