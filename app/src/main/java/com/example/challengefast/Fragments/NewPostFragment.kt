package com.example.challengefast.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.challengefast.Models.Post
import com.example.challengefast.R
import com.example.challengefast.Utility
import kotlinx.android.synthetic.main.new_post_form_fragment.*
import kotlinx.android.synthetic.main.post_layout.*
import kotlin.math.log

class NewPostFragment : Fragment() {
    var newPost: Post? = null
    private val REQUEST_PICK_PHOTO = 1
    private var uriPhoto: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.new_post_form_fragment, container, false)
        Log.i("FRG", "add fragment")
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*if(!!challenge_tag_form.text.isEmpty() && !!challenge_description_form.text.isEmpty() && !!challenge_name_form.text.isEmpty()){
            newPost = Post(challenge_name_form.text.toString(),challenge_tag_form.text.toString(),challenge_description_form.text.toString(),R.drawable.bcg_dark)

        }*/

        //add media
        add_media_btn.setOnClickListener(View.OnClickListener {
            if (uriPhoto != null) {
                Toast.makeText(context, "You can't pick other photo", Toast.LENGTH_SHORT).show()
            } else {

                val intent = Intent()
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_PHOTO)
            }
        })

        //delete media
        delete_picked_media.setOnClickListener(View.OnClickListener {
            deleteImage()
        })

        //submit creation
        submit_new_post.setOnClickListener {
            var title : String = challenge_name_form.text.toString()
            var tag : String = challenge_tag_form.text.toString()
            var description : String = challenge_description_form.text.toString()
            if(title != "" && tag != "" && description != "") {
                newPost = Post(
                    title,
                    tag,
                    description,
                    R.drawable.bcg_dark
                )
                Utility.PostsDataSource.add(newPost!!)
                loadNextFragment()
            }else{
                Toast.makeText(context, "You must fill all informations", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_PICK_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    Toast.makeText(context, "An error have been occured", Toast.LENGTH_SHORT).show()
                    Log.i("MEDIA","An error have been occured")
                }
                val clipData = data!!.clipData
                if (clipData != null) { // handle single photo
                    uriPhoto = clipData.getItemAt(0).uri
                    Log.i("MEDIA","Photo picked ${uriPhoto} from clipdata")
                }else{
                    uriPhoto= data?.data
                    Log.i("MEDIA","Photo picked ${uriPhoto} from data")
                }
                displayImage(this.uriPhoto!!)
            }
        }
    }
    fun displayImage(image: Uri) {
        //picked_media_photo.setImageURI(image)
        //for test i use from drawable my phone is sick hhhh
        picked_media_photo.setImageResource(R.drawable.bcg_dark)
        delete_picked_media.visibility = View.VISIBLE
    }
    fun deleteImage(){
        picked_media_photo.setImageResource(R.drawable.ic_error)
        delete_picked_media.visibility = View.INVISIBLE
        this.uriPhoto = null
    }

    private fun loadNextFragment(){
        var fragment : PostsFragment = PostsFragment()
        activity!!.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frame_container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }
}
