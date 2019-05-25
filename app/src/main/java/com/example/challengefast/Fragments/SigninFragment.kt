package com.example.challengefast.Fragments


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.R

import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.signin_fragment.*


class SigninFragment : Fragment() {

    private val TAG = "LoginFragment"
    //global variables
    private var email: String? = null
    private var password: String? = null
    //UI elements

    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var mProgressBar: ProgressDialog? = null
    //Firebase references
    private var mAuth: FirebaseAuth? = null

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

              loginUser()
        })

        tv_forgot_password.setOnClickListener(View.OnClickListener {
                sendPasswordResetEmail()

        })
    }

    private fun initialise() {

        etEmail = email_address_signin
        etPassword =password_signin
        btnLogin = submit_signin

        mProgressBar = ProgressDialog(this.activity)
        mAuth = FirebaseAuth.getInstance()
       /* tvForgotPassword!!
            .setOnClickListener { startActivity(Intent(this@LoginActivity,
                ForgotPasswordActivity::class.java)) }*/

        btnLogin!!.setOnClickListener { loginUser() }
    }
    private fun loginUser() {
        initialise()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mProgressBar!!.setMessage("Connexion...")
            mProgressBar!!.show()
            Log.d(TAG, "Logging in user.")
            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this.activity!!) { task ->
                    mProgressBar!!.hide()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")

                        var intent : Intent = Intent(activity,NavigationActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this.activity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this.activity, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendPasswordResetEmail() {
        initialise()
        val email = etEmail?.text.toString()
        if (!TextUtils.isEmpty(email)) {
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val message = "Email sent."
                        Log.d(TAG, message)
                        Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
                        var intent : Intent = Intent(activity,NavigationActivity::class.java)
                        startActivity(intent)
                        //updateUI()
                    } else {
                        Log.w(TAG, task.exception!!.message)
                        Toast.makeText(this.activity, "No user found with this email.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this.activity, "Enter Email", Toast.LENGTH_SHORT).show()
        }
    }

}