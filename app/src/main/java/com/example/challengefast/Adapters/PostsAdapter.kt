package com.example.challengefast.Adapters

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.support.v7.app.AlertDialog
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
import com.example.challengefast.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.android.synthetic.main.post_layout.view.*

class PostsAdapter : BaseAdapter {
    var context : Context?= null
    var modelList = ArrayList<Post>()
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private var mUser :FirebaseUser?= null



    constructor(context : Context,modelList: ArrayList<Post>){
        this.context = context
        this.modelList = modelList
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutItem = inflator.inflate(R.layout.post_layout,null)
        val item = modelList.get(position)
        mAuth = FirebaseAuth.getInstance()
         mUser = mAuth!!.currentUser

        //put data
        layoutItem.title_post.text = item.challenge.replace("\n","")
        layoutItem.tag_post.text = item.tag.replace("\n","")
        layoutItem.description_post.text = item.description.replace("\n","")

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

                layoutItem.user_full_name_post.text = full_name
                layoutItem.user_country_post.text= snapshot . child ("country").value!! as String

                if (item.userId.equals(mUser!!.uid)){

                    layoutItem.fork_post.visibility=View.INVISIBLE
                    layoutItem.iv_check.visibility=View.INVISIBLE
                    layoutItem.tv_accepted.visibility=View.INVISIBLE

                }
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

        val challengeListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val forkedChallengesList = ArrayList<Post>()
                var snapshotIterable: Iterable<DataSnapshot>  = dataSnapshot.children
                var iterator: Iterator<DataSnapshot> = snapshotIterable.iterator()
                var found=false
                while ((iterator.hasNext())&& (!found)){
                    val forkedChallenges = iterator.next()
                    if (item.key.equals(forkedChallenges.child("post_id").value.toString()))
                    {found=true
                        layoutItem.fork_post.visibility=View.INVISIBLE
                        layoutItem.iv_check.visibility=View.VISIBLE
                        layoutItem.tv_accepted.visibility=View.VISIBLE}


                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message")
            }
        }
        var Forkedref:DatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mUser!!.uid).child("forkedChallenges")
        Forkedref.addValueEventListener(challengeListener)
        //accept  the challenge

        if (layoutItem.fork_post.visibility.equals(View.VISIBLE)){
        layoutItem.fork_post.setOnClickListener{

            showAcceptChallengeDialog(item,layoutItem)



        }}


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

    private fun showAcceptChallengeDialog(item :Post,itemView : View)
    {
        val builder = AlertDialog.Builder(this.context!!,R.style.MyDialogTheme)

        // Set the alert dialog title
        builder.setTitle("${item.challenge} challenge")


        // Display a message on alert dialog
        builder.setMessage("Are you sure you want to accept this challenge ?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->

            acceptChallenge(item,itemView)

        }


        // Display a negative button on alert dialog
        builder.setNegativeButton("No"){dialog,which ->

        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()


        // Display the alert dialog on app interface
        dialog.show()
    }

    private fun acceptChallenge(item :Post,layoutItem : View)
    {
        Log.i("her","nutton clicked")


        var Ref:DatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mUser!!.uid).child("forkedChallenges").push()
        Ref.child("key").setValue(Ref.key)
        Ref.child("post_id").setValue(item.key).
            addOnSuccessListener { taskSnapshot ->

                DynamicToast.makeSuccess(context!!, "Amazing ! start challenging from now ",Toast.LENGTH_LONG).show();

                layoutItem.fork_post.visibility=View.INVISIBLE
                layoutItem.iv_check.visibility=View.VISIBLE
                layoutItem.tv_accepted.visibility=View.VISIBLE
                //NavigationActivity.postsList.add(newPost!!)
                Log.i("FRG","moving next fragmnet")
            }.addOnFailureListener { error ->

            Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
        }}

}
