package com.example.challengefast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.challengefast.Adapters.PostsAdapter
import com.example.challengefast.R

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
}