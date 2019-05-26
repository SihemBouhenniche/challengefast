package com.example.challengefast.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.challengefast.R
import com.example.challengefast.Utility
import kotlinx.android.synthetic.main.post_layout.view.*

class PostsAdapter : BaseAdapter {
    var context : Context?= null
    constructor(context : Context){
        this.context = context
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutItem = inflator.inflate(R.layout.post_layout,null)
        val item = Utility.PostsDataSource.get(position)
        //put data
        layoutItem.title_post.text = item.challenge
        layoutItem.tag_post.text = item.tag
        layoutItem.media_post.setImageURI(Uri.parse(item.media))
        //only for test we use image from drawble
        if(item.media == "")
            layoutItem.media_post.setImageResource(R.drawable.iftar)
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
        return Utility.PostsDataSource.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return Utility.PostsDataSource.size
    }

}