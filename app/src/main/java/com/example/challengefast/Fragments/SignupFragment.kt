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
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast

import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.Adapters.CountrySpinnerAdapter
import com.example.challengefast.R
import com.example.challengefast.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.signup_fragment.*

class SignupFragment : Fragment() {
    lateinit var countrySpinner: Spinner
    lateinit var countryIdValue: String
    lateinit var countryCodeValue: String
    lateinit var countryNameValue: String

    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null
    //UI elements
    private var mProgressBar: ProgressDialog? = null
    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.signup_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        countrySpinner = activity!!.findViewById(R.id.spinner)
        SetupCountryPicker()
        submit_signup.setOnClickListener(View.OnClickListener {
            createNewAccount()

        })
    }

    private fun createNewAccount() {
            initialise()
        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {


        mProgressBar!!.setMessage("Registering User...")
        mProgressBar!!.show()
        mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this.activity!!) { task ->
                mProgressBar!!.hide()
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("CreateAccountActivity", "createUserWithEmail:success")
                    val userId = mAuth!!.currentUser!!.uid
                    //Verify Email
                    verifyEmail()
                    var intent : Intent = Intent(activity,NavigationActivity::class.java)
                    startActivity(intent)
                    //update user profile information
                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("firstName").setValue(firstName)
                    currentUserDb.child("lastName").setValue(lastName)
                    currentUserDb.child("country").setValue(countryNameValue)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.d( "CreateAccountActivity", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this.activity
                        , "${task.exception!!.message}",
                        Toast.LENGTH_SHORT).show()
                }


    }
        } else {
            Toast.makeText(this.activity, "Enter all details", Toast.LENGTH_SHORT).show()
        }}

    private fun initialise() {
        firstName = first_name.text.toString()
        lastName = last_name.text.toString()
        email = email_address.text.toString()
        password = Password.text.toString()
        mProgressBar = ProgressDialog(this.context)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()



    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this.activity!!) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.activity!!,
                        "Verification email sent to " + mUser.getEmail(),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("signup fragment", "sendEmailVerification", task.exception)
                    Toast.makeText(this.activity,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun SetupCountryPicker() {
        //the countryPickerData holds a list of objects of the class ExPickerItem.
        val countryPickerData = Utility.COUNTRY_DATA_SOURCE
        Log.d("xxx", countryPickerData.toString())
        val pickerAdapter = CountrySpinnerAdapter(context!!,
            R.layout.file_item_country, countryPickerData)
        countrySpinner.setAdapter(pickerAdapter)
        //have the first item by-default selected
        countrySpinner.setSelection(1)
        countryIdValue = countryPickerData.get(0).id!!
        countryCodeValue = countryPickerData.get(0).code!!
        countryNameValue = countryPickerData.get(0).name!!
        //Adding a listener to the custom spinner when an item is selected from the spinner
        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                countryIdValue = countryPickerData.get(i).id!!
                countryCodeValue = countryPickerData.get(i).code!!
                countryNameValue = countryPickerData.get(i).name!!
                //Toast.makeText(context!!, "You selected $countryNameValue with id $countryIdValue and code $countryCodeValue", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
                //handle when no item selected
            }
        }
    }
}