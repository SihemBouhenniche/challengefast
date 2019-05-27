package com.example.challengefast.Activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.challengefast.Fragments.NewPostFragment
import com.example.challengefast.Fragments.PostsFragment
import com.example.challengefast.Fragments.ProfileFragment
import com.example.challengefast.Models.Post
import com.example.challengefast.R
import com.example.challengefast.Utility
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {
    private val TAG = "UtilityActivity"
    companion object{
        var postsList = ArrayList<Post>()
    }

    private var mPostsReference: DatabaseReference? = null
    private var mPostsListener: ValueEventListener? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val fragment = PostsFragment()
                addFragment(fragment)
                Log.i("FRG","add fragment")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add -> {
                val fragment = NewPostFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                val fragment = ProfileFragment()
                addFragment(fragment)
                Log.i("FRG","add fragment")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        Toast.makeText(this,"load fragment",Toast.LENGTH_SHORT)
        Log.i("FRG","add fragment")

        mPostsReference = FirebaseDatabase.getInstance().getReference("Posts")
    }

    override fun onStart() {
        super.onStart()

        val messageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var snapshotIterable: Iterable<DataSnapshot>  = dataSnapshot.children
                var iterator: Iterator<DataSnapshot> = snapshotIterable.iterator()
                while (iterator.hasNext()){
                    val post = iterator.next()
                    /*if(post is Map<*, *>){
                        Log.i(TAG, "${post.get("challenge")}")
                    }else{
                        Log.i(TAG, "${post.child("challenge")} from else")
                    }
                    Log.e(TAG, "${iterator.next().get}")
                    val post = iterator.next().getValue(Post::class.java)
                    postsList.add(post!!)*/
                    val objPost = Post(post.child("challenge").value.toString()
                        ,post.child("tag").value.toString(),
                        post.child("description").value.toString(),
                        post.child("media").value.toString(),
                        post.child("key").value.toString(),
                        post.child("state").value.toString().toInt())
                    postsList.add(objPost!!)
                }
                val fragment = PostsFragment()
                addFragment(fragment)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message")
            }
        }

        mPostsReference!!.addValueEventListener(messageListener)

        // copy for removing at onStop()
        mPostsListener = messageListener
    }

    override fun onStop() {
        super.onStop()

        if (mPostsListener != null) {
            mPostsReference!!.removeEventListener(mPostsListener!!)
        }
    }

    private fun addFragment(fragment: Fragment) {
        Log.i("FRG","add fragment")
        Toast.makeText(this,"load fragment",Toast.LENGTH_SHORT)
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

}