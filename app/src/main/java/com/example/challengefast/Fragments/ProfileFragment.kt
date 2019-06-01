package com.example.challengefast.Fragment

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.Fragments.BlankFragment
import com.example.challengefast.Fragments.UserChallengesFragment
import com.example.challengefast.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.profile_fragment.*


class ProfileFragment : Fragment() {
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    val fragment = UserChallengesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialise()
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {


            R.id.navigation_profile_my_posts -> {
                if (NavigationActivity.userPostsList.size==0) {
                    val fragment =BlankFragment()
                    addFragment(fragment)
                }
                else{

                    val fragment = UserChallengesFragment.newInstance(0)
                    addFragment(fragment)
                }

            }
            R.id.navigation_profile_other_posts -> {

                if (NavigationActivity.forkedPostsList.size==0) {
                    val fragment = BlankFragment()
                    addFragment(fragment)

                }
                else{
                    val fragment = UserChallengesFragment.newInstance(1)
                    addFragment(fragment)

                }
            }

        }
        false
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.profile_fragment, container, false)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigation_profile.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val mUser = mAuth!!.currentUser
        email_profile.text =mUser!!.email
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val last_name=snapshot . child ("lastName").value!! as String
                val first_name=snapshot . child ("firstName").value!! as String
                val full_name :String =last_name.plus(" ").plus(first_name)
                if ((   user_full_name_profile!=null)&&(country_profile!=null)){
               user_full_name_profile.text = full_name
                country_profile.text= snapshot . child ("country").value!! as String}
            }




        })    }
    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()}


    private fun addFragment(fragment: Fragment) {

        (context as NavigationActivity).supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.profile_frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }



    }

