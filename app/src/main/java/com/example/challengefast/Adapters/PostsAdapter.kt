package com.example.challengefast.Adapters

import android.content.Context
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
        layoutItem.media_post.setImageResource(item.media)
        layoutItem.description_post.text = item.description
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