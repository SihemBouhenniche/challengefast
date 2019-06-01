package com.example.challengefast.Filters

import android.util.Log
import android.widget.Filter
import com.example.challengefast.Adapters.PostsAdapter
import com.example.challengefast.Models.Post

class PostFilter : Filter {
    lateinit var postsAdapter : PostsAdapter
    constructor(postsAdapter: PostsAdapter){
        this.postsAdapter = postsAdapter
    }
    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var listPosts = postsAdapter.modelList
        var  filterResults:FilterResults =  FilterResults ()
        if (constraint!=null && constraint.length>0) {

            var  tempList: ArrayList<Post>  =  ArrayList<Post>();

            // search content in friend list
            for (post :Post in listPosts ) {
                Log.i("FILTER",post.challenge)
                if (post.challenge!!.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                    tempList.add(post)
                    Log.i("FILTER BCL",post.challenge)
                }
            }

            filterResults.count = tempList.size
            filterResults.values = tempList

        } else {
            filterResults.count = listPosts.size
            filterResults.values =listPosts
        }

        return filterResults;
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        var filteredList = results!!.values  as ArrayList<Post>
        postsAdapter.filteredList = filteredList
        postsAdapter.notifyDataSetChanged()
    }
}