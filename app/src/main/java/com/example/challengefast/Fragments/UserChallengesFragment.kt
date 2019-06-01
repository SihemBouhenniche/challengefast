package com.example.challengefast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.challengefast.Activities.NavigationActivity
import com.example.challengefast.Adapters.UserChallengesAdapter
import com.example.challengefast.Models.Post
import com.example.challengefast.R


class UserChallengesFragment() : Fragment() {
    var listPosts : ListView?= null
    var listPostsAdapter : UserChallengesAdapter?= null

    companion object {
        fun newInstance(type: Int): UserChallengesFragment {
            val fragment = UserChallengesFragment()
            val args = Bundle()
            args.putInt("type", type)
            fragment.setArguments(args)
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        val type = args!!.getInt("type", 0)
        if (type==0){
        listPostsAdapter = UserChallengesAdapter(activity!!, NavigationActivity.userPostsList,0)}
        if (type==1){
            listPostsAdapter = UserChallengesAdapter(activity!!, buildList(),1)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var rootView = inflater!!.inflate(R.layout.user_challenges_fragment, container, false)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = arguments

        listPosts = activity!!.findViewById(R.id.challenges_list)


        listPosts!!.adapter = listPostsAdapter
    }

    private fun buildList():ArrayList<Post>{
        val pIt:Iterator<Post> = NavigationActivity.postsList.iterator()
        val fIt:Iterator<String> = NavigationActivity.forkedPostsList.iterator()
          var list:ArrayList<Post> = ArrayList<Post>()

        while(pIt.hasNext())
        {    val post=pIt.next()
            while(fIt.hasNext()){
            if (post.key.equals(fIt.next()))
            {
                list.add(post)
            }

        }
        }
        return list
    }


}