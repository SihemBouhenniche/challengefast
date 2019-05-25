package com.example.challengefast.Activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.challengefast.Fragments.PostsFragment
import com.example.challengefast.Fragments.ProfileFragment
import com.example.challengefast.R
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val fragment = PostsFragment()
                addFragment(fragment)
                Toast.makeText(this,"load fragment",Toast.LENGTH_SHORT)
                Log.i("FRG","add fragment")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add -> {
                Toast.makeText(this,"load fragment",Toast.LENGTH_SHORT)
                Log.i("FRG","add fragment")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                val fragment = ProfileFragment()
                addFragment(fragment)
                Toast.makeText(this,"load fragment",Toast.LENGTH_SHORT)
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
        
        val fragment = PostsFragment()
        addFragment(fragment)
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