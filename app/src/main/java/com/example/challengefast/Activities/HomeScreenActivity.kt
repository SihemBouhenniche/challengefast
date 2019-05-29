package com.example.challengefast.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.challengefast.Fragments.SigninFragment
import com.example.challengefast.Fragments.SignupFragment
import com.example.challengefast.R






class HomeScreenActivity : AppCompatActivity() {
    lateinit var applogo: ImageView
    lateinit var homescreen: ConstraintLayout
    private var content: FrameLayout? = null

   private  var sp: SharedPreferences? = null

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp=getSharedPreferences("login",MODE_PRIVATE);
        if (sp!!.contains("username") && sp!!.contains("password")) {
            startActivity(Intent(this, NavigationActivity::class.java))
            finish()   //finish current activity
        }

        setContentView(com.example.challengefast.R.layout.activity_home_screen)

        applogo = findViewById(com.example.challengefast.R.id.logo)
        homescreen = findViewById(com.example.challengefast.R.id.home_screen)

        var myanim: Animation = AnimationUtils.loadAnimation(this, com.example.challengefast.R.anim.welcom_animation)
        var animright: Animation = AnimationUtils.loadAnimation(this, com.example.challengefast.R.anim.welcome_btn_animation_right)
        var animleftt: Animation = AnimationUtils.loadAnimation(this, com.example.challengefast.R.anim.welcome_btn_animation_left)
        applogo.startAnimation(myanim)
        homescreen.startAnimation(myanim)
        findViewById<Button>(com.example.challengefast.R.id.signin).startAnimation(animright)
        findViewById<Button>(com.example.challengefast.R.id.signup).startAnimation(animleftt)
        findViewById<Button>(com.example.challengefast.R.id.signup).setOnClickListener(View.OnClickListener {
            val fragment = SignupFragment()
            addFragment(fragment)
        })


        findViewById<Button>(R.id.signin).setOnClickListener(View.OnClickListener {

            val fragment = SigninFragment()
            addFragment(fragment)
        })
    }

    private fun addFragment(fragment: android.support.v4.app.Fragment) {
        supportFragmentManager
            .beginTransaction()
.setCustomAnimations(com.example.challengefast.R.anim.design_bottom_sheet_slide_in, com.example.challengefast.R.anim.design_bottom_sheet_slide_out)
            .replace(com.example.challengefast.R.id.container_form, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

}


