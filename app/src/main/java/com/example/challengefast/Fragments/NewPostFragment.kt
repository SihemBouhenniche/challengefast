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
import android.widget.Toast
import com.example.challengefast.Models.Post
import com.example.challengefast.R
import com.example.challengefast.Utility
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.new_post_form_fragment.*

class NewPostFragment : Fragment() {
    var newPost: Post? = null
    private val REQUEST_PICK_PHOTO = 1
    private var uriPhoto: Uri = Uri.EMPTY
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

        //add media
        add_media_btn.setOnClickListener {
            openGallery()
        }

        //delete media
        delete_picked_media.setOnClickListener{
            deleteImage()
        }

        //submit creation
        submit_new_post.setOnClickListener {
            submit_new_post.visibility = View.INVISIBLE
            spinner_progress.visibility = View.VISIBLE
            addNewPost()
            loadNextFragment()
        }
    }

    //private function open gallery
    private fun openGallery(){
        if (uriPhoto != Uri.EMPTY) {
            Toast.makeText(context, "You can't pick other photo", Toast.LENGTH_SHORT).show()
        } else {
            //open gallery
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_PHOTO)
        }
    }

    //private function for add new post
    private fun addNewPost(){
        var title : String = challenge_name_form.text.toString()
        var tag : String = challenge_tag_form.text.toString()
        var description : String = challenge_description_form.text.toString()

        if(title != "" && tag != "" && description != "" && uriPhoto != Uri.EMPTY) {
            //every thing is okay create new post and add it to firebase
            //upload the image first
            var storageReference : StorageReference = FirebaseStorage.getInstance().reference.child("challenge_photos")
            var imgFilePath : StorageReference = storageReference.child(uriPhoto.lastPathSegment)
            imgFilePath.putFile(uriPhoto).addOnSuccessListener { taskSnapshot ->
                //create new post
                newPost = Post(
                    title,
                    tag,
                    description,
                    taskSnapshot.uploadSessionUri.toString()
                )
                //add post to firebase database
                putPostInFirebase(newPost!!)
            }.addOnFailureListener { error ->
                    submit_new_post.visibility = View.VISIBLE
                    spinner_progress.visibility = View.INVISIBLE
                    Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
            }
            Utility.PostsDataSource.add(newPost!!)
        }else{
            Toast.makeText(context, "You must fill all inputs", Toast.LENGTH_SHORT).show()
        }
    }
    private fun putPostInFirebase(post: Post){
        var postRef:DatabaseReference = FirebaseDatabase.getInstance().getReference("Posts").push()
        post.key =  postRef.key.toString()
        postRef.setValue(post).addOnSuccessListener { taskSnapshot ->
            Toast.makeText(context, "Challenge added successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { error ->
            submit_new_post.visibility = View.VISIBLE
            spinner_progress.visibility = View.INVISIBLE
            Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
        }
    }
    //private fucntion for delete picked image
    private fun deleteImage(){
        picked_media_photo.setImageResource(R.drawable.ic_error)
        delete_picked_media.visibility = View.INVISIBLE
        this.uriPhoto = Uri.EMPTY
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_PICK_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    Toast.makeText(context, "An error have been occured", Toast.LENGTH_SHORT).show()
                    Log.i("MEDIA","An error have been occured")
                }
                uriPhoto= data?.data!!
                Log.i("MEDIA","Photo picked ${uriPhoto} from data")
                displayImage(this.uriPhoto!!)
            }
        }
    }
    fun displayImage(image: Uri) {
        picked_media_photo.setImageURI(image)
        //for test i use from drawable my phone is sick hhhh
        //picked_media_photo.setImageResource(R.drawable.bcg_dark)
        delete_picked_media.visibility = View.VISIBLE
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
