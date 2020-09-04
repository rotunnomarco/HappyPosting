package com.example.happyposting.ui.postcomments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happyposting.classes.Comment
import com.example.happyposting.databinding.LayoutEntryCommentBinding

class PostCommentsAdapter(
    var comments: MutableList<Comment>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LayoutEntryCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CommentViewHolder).bind(comments[position])

    }

    override fun getItemCount(): Int {
        return comments.size
    }

    class CommentViewHolder(
        private val binding: LayoutEntryCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Comment) {
            binding.textName.text = item.email + "\n" + item.name
            binding.textBody.text = item.body
        }
    }
}