package com.example.happyposting.ui.posts

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happyposting.R
import com.example.happyposting.ui.ConfirmDeleteDialogFragment
import com.example.happyposting.ui.postcomments.PostCommentsFragment
import com.example.happyposting.ui.posts.editable.EditPostFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_post.*


class PostFragment : Fragment(R.layout.fragment_post), PostAdapterListener {
    private val viewModel: PostViewModel by viewModels()
    private val postAdapter: PostAdapter by lazy {
        PostAdapter(
            mutableListOf(), this
        )
    }
    private var editPostFragment = EditPostFragment(postAdapter.posts)
    private var commentsFragment = PostCommentsFragment(postAdapter.posts)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        recyclerview_Posts.adapter = postAdapter
        recyclerview_Posts.layoutManager = LinearLayoutManager(context)

        recyclerview_Posts.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        postAdapter.notifyDataSetChanged()
    }

    private fun showSheet(id: Int) {
        val mBottomSheetDialog = BottomSheetDialog(requireActivity())
        val sheetView: View = requireActivity().layoutInflater.inflate(
            R.layout.fragment_bottomsheet,
            null
        )
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()
        val edit =
            sheetView.findViewById<View>(R.id.fragment_history_bottom_sheet_edit) as LinearLayout
        val delete =
            sheetView.findViewById<View>(R.id.fragment_history_bottom_sheet_delete) as LinearLayout

        edit.setOnClickListener {
            editPostFragment = EditPostFragment(postAdapter.posts)
            (it.context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentLayout, editPostFragment)
                .addToBackStack(null)
                .commit()

            editPostFragment.editPost(id)
            mBottomSheetDialog.hide()
        }

        delete.setOnClickListener {
            val confirmDeleteDialogFragment = ConfirmDeleteDialogFragment(postAdapter, id)
            confirmDeleteDialogFragment.show(this.requireActivity().supportFragmentManager, null)

            mBottomSheetDialog.hide()
        }
    }

    private fun changeToCommentsFragment() {
        commentsFragment = PostCommentsFragment(postAdapter.posts)
        this.requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragmentLayout,
                commentsFragment
            )
            addToBackStack(null)
            commitAllowingStateLoss()
        }
    }

    private fun changeCommentsFragmentWithPosition(position: Int) {
        changeToCommentsFragment()
        commentsFragment.openPostComments(position)
    }

    private fun addObservers() {
        viewModel.post.observe(viewLifecycleOwner, Observer { post ->
            viewModel.addListPost(post)
        })
        viewModel.trackLiveData.observe(viewLifecycleOwner, Observer { post ->
            if (post.isNullOrEmpty()) {
                viewModel.getPost()
            } else {
                postAdapter.posts.clear()
                postAdapter.posts.addAll(post)
                postAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onClick(id: Int) {
        changeCommentsFragmentWithPosition(id)
    }

    override fun onLongClick(id: Int) {
        showSheet(id)
    }
}
