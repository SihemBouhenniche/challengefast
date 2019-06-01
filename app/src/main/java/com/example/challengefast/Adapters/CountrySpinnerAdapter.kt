package com.example.challengefast.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.challengefast.R
import com.example.challengefast.Models.Country

class CountrySpinnerAdapter : ArrayAdapter<Country>{
    private var customCountries: List<Country>

    constructor(context: Context, resource: Int, pickerItems: List<Country>) : super(context, resource, pickerItems){
        this.customCountries = pickerItems
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return CustomSpinnerView(position, convertView, parent)
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return CustomSpinnerView(position, convertView, parent)
    }
    private fun CustomSpinnerView(position: Int, convertView: View?, parent: ViewGroup): View {
        //Getting the Layout Inflater Service from the system
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //Inflating out custom spinner view
        val customView = layoutInflater.inflate(R.layout.file_item_country, parent, false)
        //Declaring and initializing the widgets in custom layout
        val imageView = customView.findViewById(R.id.imgflag) as ImageView
        val textView = customView.findViewById(R.id.textcountry) as TextView
        //displaying the data
        //drawable items are mapped with name prefixed with 'zx_' also image names are containing underscore instead of spaces.
        val imageRef = customCountries[position].code!!.toLowerCase()
        val resID = context.resources.getIdentifier(imageRef, "drawable", context.packageName)
        imageView.setImageResource(resID)
        //imageView.setImageDrawable();geResource(getApplicationContext(),getImageId(customCountries.get(position).getName()));
        textView.setText(customCountries[position].name)
        return customView
    }
}

