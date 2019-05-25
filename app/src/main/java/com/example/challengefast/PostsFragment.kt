package com.example.challengefast

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.post_layout.view.*

class PostsFragment : Fragment() {
    var listPosts : ListView ?= null
    var listPostsAdapter : PostsAdapter ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPostsAdapter = PostsAdapter(activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.posts_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listPosts = activity!!.findViewById(R.id.posts_list_view)
        listPosts!!.adapter = listPostsAdapter
    }

    class PostsAdapter : BaseAdapter{
        var context : Context ?= null
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
}