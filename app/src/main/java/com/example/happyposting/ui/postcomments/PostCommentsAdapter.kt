package com.example.happyposting.ui.postcomments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happyposting.classes.Comment
import com.example.happyposting.classes.Image
import com.example.happyposting.classes.PostAdapterItem
import com.example.happyposting.databinding.LayoutEntryCommentBinding
import com.example.happyposting.databinding.LayoutEntryCommentImageBinding


class PostCommentsAdapter(
    var dataSet: MutableList<PostAdapterItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val binding =
                LayoutEntryCommentImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return ImageViewHolder(binding)
        } else {
            val binding =
                LayoutEntryCommentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return CommentViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataSet[position]) {
            is Comment -> 1
            is Image -> 0
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommentViewHolder) {
            holder.bind(dataSet[position] as Comment)
        } else if (holder is ImageViewHolder) {
            holder.bind(dataSet[position] as Image)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
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

    class ImageViewHolder(
        private val binding: LayoutEntryCommentImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            binding.myImage.setImageBitmap(image.bitmapToLoad)
        }
    }
}


