package com.example.happyposting.ui.posts.editable

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.happyposting.R
import com.example.happyposting.classes.Post
import com.example.happyposting.ui.posts.PostFragment
import com.example.happyposting.ui.posts.PostViewModel
import kotlinx.android.synthetic.main.fragment_post_modify.*

class EditPostFragment(
    private var posts: MutableList<Post>
) : Fragment(R.layout.fragment_post_modify) {

    private lateinit var viewModel: PostViewModel

    private var postPosition: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edit_postBody.setText(posts[postPosition].body)
        edit_postText.setText(posts[postPosition].title)

        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        confirmPostModifyFAB.setOnClickListener {
            posts[postPosition].body = edit_postBody.text.toString()
            posts[postPosition].title = edit_postText.text.toString()
            viewModel.updatePost(posts[postPosition])
            postPosition = -1
            this.requireActivity().onBackPressed()
        }

        backToPostsFragmentFAB.setOnClickListener {
            postPosition = -1
            this.requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragmentLayout,
                    PostFragment()
                )
                commit()
            }
        }
    }

    fun editPost(position: Int) {
        postPosition = posts.indexOfFirst {
            it.id == position
        }
    }
}