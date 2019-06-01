package com.example.challengefast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.Adapters.PostsAdapter
import com.example.challengefast.R
import kotlinx.android.synthetic.main.posts_fragment.*

class PostsFragment : Fragment() {
    var listPosts : ListView ?= null
    var listPostsAdapter : PostsAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listPostsAdapter = PostsAdapter(activity!!, NavigationActivity.postsList)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.posts_fragment, container, false)
        Log.i("FRG","add fragment")
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listPosts = activity!!.findViewById(R.id.posts_list_view)
        listPosts!!.adapter = listPostsAdapter

        search_field.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    listPosts!!.clearTextFilter();
                }
                else {
                    listPosts!!.setFilterText(newText.toString());
                }

                return true;

            }
        })
    }
}