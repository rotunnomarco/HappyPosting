package com.example.happyposting.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.happyposting.R
import com.example.happyposting.ui.posts.PostAdapter
import com.example.happyposting.ui.posts.PostViewModel

class ConfirmDeleteDialogFragment(private var postAdapter: PostAdapter, private var postId: Int) :
    DialogFragment() {
    private val viewModel: PostViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Delete Post?")
                .setPositiveButton(R.string.delete,
                    DialogInterface.OnClickListener { dialog, id ->
                        postAdapter.deleteItemAtPosition(postId)
                        viewModel.deletePost(postId)
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}