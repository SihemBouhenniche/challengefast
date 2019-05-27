package com.example.challengefast.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.Models.Post
import com.example.challengefast.R
import kotlinx.android.synthetic.main.post_layout.view.*

class PostsAdapter : BaseAdapter {
    var context : Context?= null
    var modelList = ArrayList<Post>()
    constructor(context : Context,modelList: ArrayList<Post>){
        this.context = context
        this.modelList = modelList
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutItem = inflator.inflate(R.layout.post_layout,null)
        val item = modelList.get(position)
        //put data
        layoutItem.title_post.text = item.challenge
        layoutItem.tag_post.text = item.tag
        var imageView : ImageView = layoutItem.media_post
        //set the image
        Glide.with(context)
            .load(Uri.parse(item.media))
            .into(imageView)


        layoutItem.media_post.setImageURI(Uri.parse(item.media))
        layoutItem.description_post.text = item.description
        layoutItem.star_btn.setOnClickListener {
            if(item.state == 1){
                layoutItem.star_btn.setBackgroundResource(R.drawable.ic_star_empty)
            }
            else{
                layoutItem.star_btn.setBackgroundResource(R.drawable.ic_star)
            }
            item.state = -1*item.state
        }
        return layoutItem
    }

    override fun getItem(position: Int): Any {
        return NavigationActivity.postsList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return NavigationActivity.postsList.size
    }

}