package com.example.happyposting.ui.postcomments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happyposting.R
import com.example.happyposting.classes.Comment
import com.example.happyposting.classes.Image
import com.example.happyposting.classes.Post
import kotlinx.android.synthetic.main.fragment_post_comments.*


class PostCommentsFragment(
    private var posts: MutableList<Post>
) : Fragment(R.layout.fragment_post_comments) {

    private var postIdPosition: Int = -1
    private var commentIdPosition: Int = -1

    private val viewModel: PostCommentsViewModel by viewModels()
    private val commentsAdapter: PostCommentsAdapter by lazy {
        PostCommentsAdapter(
            mutableListOf()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()

        recyclerview_Comments.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerview_Comments.adapter = commentsAdapter
        recyclerview_Comments.layoutManager = LinearLayoutManager(context)
        commentsAdapter.notifyDataSetChanged()

        buttonBack.setOnClickListener {
            changeToPostFragment()
            commentIdPosition = -1
            postIdPosition = -1
        }

        btnCam.setOnClickListener {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val REQUEST_CODE = 42
            startActivityForResult(takePicture, REQUEST_CODE)
        }

        buttonSend.setOnClickListener {
            if (messageInput.text.isNotEmpty()) {
                val comment = Comment(
                    null,
                    commentIdPosition.toString(),
                    "Marco",
                    "marco.rotunno@overapp.it",
                    messageInput.text.toString()
                )
                viewModel.addComment(comment)
                commentsAdapter.dataSet.add(comment)
                recyclerview_Comments.scrollToPosition(commentsAdapter.dataSet.size - 1)
                commentsAdapter.notifyDataSetChanged()
                messageInput.text.clear()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val takenImage = data?.extras?.get("data") as Bitmap
        commentsAdapter.dataSet.add(Image(takenImage))
        commentsAdapter.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun openPostComments(postId: Int) {
        postIdPosition = posts.indexOfFirst { it.id == postId }
        commentIdPosition = postId
    }

    override fun onResume() {
        super.onResume()
        if (postIdPosition != -1) {
            textCommentBody.text = posts[postIdPosition].body
            textCommentTitle.text = posts[postIdPosition].title
        }
    }

    private fun changeToPostFragment() {
        this.requireActivity().onBackPressed()
    }

    private fun addObservers() {
        viewModel.comment.observe(viewLifecycleOwner, Observer { comment ->
            viewModel.addListComment(comment)
        })

        viewModel.getAllCommentsWithId(commentIdPosition)

        viewModel.trackLiveComments.observe(viewLifecycleOwner, Observer { comment ->
            if (comment.isNullOrEmpty()) {
                viewModel.getNetworkCommentsWithId(commentIdPosition)
            } else {
                commentsAdapter.dataSet.clear()
                commentsAdapter.dataSet.addAll(comment)
                commentsAdapter.notifyDataSetChanged()
            }
        })
    }
}


