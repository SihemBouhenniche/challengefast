package com.example.challengefast.Adapters

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.Fragments.PostDetailsFragment
import com.example.challengefast.Fragments.PostsFragment
import com.example.challengefast.Models.Post
import com.example.challengefast.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.own_post_layout.view.*

class UserChallengesAdapter : BaseAdapter {
    var context : Context?= null
    var challengeList = ArrayList<Post>()
    var type:Int

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutItem = inflator.inflate(R.layout.own_post_layout,null)

        if (type==1){
            layoutItem.delete_post.visibility=View.INVISIBLE
        }
        if (type==0){
            layoutItem.delete_post.setOnClickListener {
                showDeletetChallengeDialog(challengeList[position])

                 }
        }
        layoutItem.challenge_name.text= challengeList[position].challenge


        layoutItem.view_post.setOnClickListener{
            loadDetailsFragment(position)

        }
        return layoutItem
    }


    constructor(context : Context,List: ArrayList<Post>,type:Int){
        this.context = context
        this.challengeList = List
        this.type=type
    }

    override fun getItem(position: Int): Any {
        return challengeList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getCount(): Int {
        return challengeList.size
    }
    private fun findPosition (position:Int):Int
    { val It:Iterator<Post> = NavigationActivity.postsList.iterator()
        var index :Int=0
        var i=-1
        while(It.hasNext())
        { i++
            if (It.next().equals(challengeList[position]))
            {  index=i
            }
        }

        return index

    }
    private fun loadDetailsFragment(position: Int) {
        var fragment = PostDetailsFragment()
        fragment.position = findPosition(position)
        (context as NavigationActivity)!!.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

    private fun deleteChallenge(item :Post)
    {

        var Ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Posts").child(item.key)
        Ref.setValue(null)
        var fragment = PostsFragment()
        (context as NavigationActivity)!!.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()

    }

    private fun showDeletetChallengeDialog(item :Post)
    {
        val builder = AlertDialog.Builder(this.context!!,R.style.MyDialogTheme)

        // Set the alert dialog title
        builder.setTitle("${item.challenge} challenge")


        // Display a message on alert dialog
        builder.setMessage("Are you sure you want to delete this challenge ?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->

            deleteChallenge(item)

        }


        // Display a negative button on alert dialog
        builder.setNegativeButton("No"){dialog,which ->

        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()


        // Display the alert dialog on app interface
        dialog.show()
    }


}