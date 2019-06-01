package com.example.challengefast.Activities

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.challengefast.Fragment.ProfileFragment
import com.example.challengefast.Fragments.NewPostFragment
import com.example.challengefast.Fragments.PostsFragment
import com.example.challengefast.Models.Post
import com.example.challengefast.Models.Star
import com.example.challengefast.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_navigation.*


class NavigationActivity : AppCompatActivity() {
    private val TAG = "UtilityActivity"
    companion object{
        var postsList = ArrayList<Post>()
        var userPostsList = ArrayList<Post>()
        var forkedPostsList = ArrayList<String>()


    }

    private var mPostsReference: DatabaseReference? = null
    private var mPostsListener: ValueEventListener? = null
    private var mForkedPostsListener: ValueEventListener? = null
    private var Forkedref:DatabaseReference? = null
    var forkedChallengeKeys = ArrayList<String>()
    var mUser:FirebaseUser?=null

    private var mAuth: FirebaseAuth? = null

init {
    mAuth = FirebaseAuth.getInstance()
    mUser=mAuth!!.currentUser


}

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val fragment = PostsFragment()
                addFragment(fragment)
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


                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //set the spinner
        loader_posts.visibility = View.VISIBLE
        frame_container.visibility = View.INVISIBLE

        mPostsReference = FirebaseDatabase.getInstance().getReference("Posts")
        Forkedref= FirebaseDatabase.getInstance().getReference("Users").child(mUser!!.uid).child("forkedChallenges")

        user_top_logo.setOnClickListener{

                showLogOutDialog()

        }
    }
    private fun showLogOutDialog()
    {
        val builder = AlertDialog.Builder(this,R.style.MyDialogTheme)

        // Set the alert dialog title
        builder.setTitle("Logout  from Challenge&Fast")


        // Display a message on alert dialog
        builder.setMessage("Are you sure you want to log out ?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->
            logOut()


        }


        // Display a negative button on alert dialog
        builder.setNegativeButton("No"){dialog,which ->

        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()


        // Display the alert dialog on app interface
        dialog.show()
    }

    private fun logOut ()
    {
        mAuth!!.signOut()
        val sp: SharedPreferences =getSharedPreferences("login", AppCompatActivity.MODE_PRIVATE);
        val e = sp!!.edit()
        e.remove("username")
        e.remove("password")
        e.commit()

        var intent : Intent = Intent(this,HomeScreenActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()


        val challengeListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var snapshotIterable: Iterable<DataSnapshot>  = dataSnapshot.children
                var iterator: Iterator<DataSnapshot> = snapshotIterable.iterator()
                var forkedkeys=ArrayList<Post>()
                while (iterator.hasNext()) {
                    val post= iterator.next()
                        if (!forkedPostsList.contains(post.child("post_id").value.toString())){

                    forkedPostsList.add(post.child("post_id").value.toString())}
                }





            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
                Log.e(ContentValues.TAG, "onCancelled: Failed to read message")
            }
        }


        val messageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                postsList = ArrayList<Post>()
                var snapshotIterable: Iterable<DataSnapshot>  = dataSnapshot.children
                var iterator: Iterator<DataSnapshot> = snapshotIterable.iterator()

                var challengeList = ArrayList<Post>()





                while (iterator.hasNext()){
                    val post = iterator.next()

                    val objPost = Post(post.child("challenge").value.toString()
                        ,post.child("tag").value.toString(),
                        post.child("description").value.toString(),
                        post.child("media").value.toString(),
                        post.child("key").value.toString(),
                        1,
                        post.child("userId").value.toString())
                    val starsList = ArrayList<Star>()
                    var snapshotIterableStar: Iterable<DataSnapshot>  = post.child("stars").children
                    var iteratorStar: Iterator<DataSnapshot> = snapshotIterableStar.iterator()

                    while (iteratorStar.hasNext()){
                        val star = iteratorStar.next()
                        val objStar = Star(star.value.toString())
                        starsList.add(objStar)
                    }
                    objPost.stars = starsList

                    /*while (PostIterator.hasNext()){

                        val key = PostIterator.next()
                        if (key.equals(  post.child("key").value.toString()))
                        {
                            forkedChallengeList.add(objPost)

                        }

                    }*/

                    postsList.add(objPost!!)

                    if ( mUser!!.uid.equals(post.child("userId").value.toString())){
                        if (!challengeList.contains(objPost)){
                            challengeList.add(objPost!!)}

                    }
                }

                userPostsList=challengeList



                if(frame_container.childCount == 0){//we are in the first interaction after login in
                    //delete spinner
                    loader_posts.visibility = View.GONE
                    frame_container.visibility = View.VISIBLE
                    val fragment = PostsFragment()
                    addFragment(fragment)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message")
            }
        }

        mPostsReference!!.addValueEventListener(messageListener)
        Forkedref!!.addValueEventListener(challengeListener)


        // copy for removing at onStop()
        mPostsListener = messageListener
        mForkedPostsListener = challengeListener
    }

    override fun onStop() {
        super.onStop()

        if (mPostsListener != null) {
            mPostsReference!!.removeEventListener(mPostsListener!!)
        }

        if (mForkedPostsListener != null) {
            Forkedref!!.removeEventListener(mForkedPostsListener!!)
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

}