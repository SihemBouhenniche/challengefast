package com.example.challengefast.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.R

import kotlinx.android.synthetic.main.signin_fragment.*


class SigninFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.signin_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        submit_signin.setOnClickListener(View.OnClickListener {
            var intent : Intent = Intent(activity, NavigationActivity::class.java)
            startActivity(intent)
        })
    }

}