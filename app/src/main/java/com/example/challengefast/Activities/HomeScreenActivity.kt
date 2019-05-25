package com.example.challengefast.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.challengefast.R
import com.example.challengefast.Fragments.SigninFragment
import com.example.challengefast.Fragments.SignupFragment


class HomeScreenActivity : AppCompatActivity() {
    lateinit var applogo : ImageView
    lateinit var homescreen : ConstraintLayout
    private var content: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        applogo = findViewById(R.id.logo)
        homescreen = findViewById(R.id.home_screen)

        var myanim : Animation = AnimationUtils.loadAnimation(this, R.anim.welcom_animation)
        var animright: Animation = AnimationUtils.loadAnimation(this, R.anim.welcome_btn_animation_right)
        var animleftt: Animation = AnimationUtils.loadAnimation(this, R.anim.welcome_btn_animation_left)
        applogo.startAnimation(myanim)
        homescreen.startAnimation(myanim)
        findViewById<Button>(R.id.signin).startAnimation(animright)
        findViewById<Button>(R.id.signup).startAnimation(animleftt)
        findViewById<Button>(R.id.signup).setOnClickListener(View.OnClickListener {
            val fragment = SignupFragment()
            addFragment(fragment)
        })
        findViewById<Button>(R.id.signin).setOnClickListener(View.OnClickListener {
            val fragment = SigninFragment()
            addFragment(fragment)
        })
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.container_form, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }
}
