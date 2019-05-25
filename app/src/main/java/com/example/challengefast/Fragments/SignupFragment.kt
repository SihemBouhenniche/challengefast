package com.example.challengefast.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
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
import kotlinx.android.synthetic.main.signup_fragment.*

class SignupFragment : Fragment() {
    lateinit var countrySpinner: Spinner
    lateinit var countryIdValue: String
    lateinit var countryCodeValue: String
    lateinit var countryNameValue: String

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
            var intent : Intent = Intent(activity, NavigationActivity::class.java)
            startActivity(intent)
        })
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
                Toast.makeText(context!!, "You selected $countryNameValue with id $countryIdValue and code $countryCodeValue", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
                //handle when no item selected
            }
        }
    }
}