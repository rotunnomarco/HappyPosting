package com.example.happyposting.ui.posts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happyposting.classes.Post
import com.example.happyposting.databinding.LayoutEntryPostBinding

interface PostAdapterListener {
    fun onClick(id: Int)
    fun onLongClick(id: Int)
}

class PostAdapter(
    val posts: MutableList<Post>,
    private val listener: PostAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LayoutEntryPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PostViewHolder(binding).also { viewHolder ->
            binding.root.setOnClickListener {
                listener.onClick(posts[viewHolder.bindingAdapterPosition].id)
            }
        }.also { viewHolder ->
            binding.root.setOnLongClickListener {
                listener.onLongClick(posts[viewHolder.bindingAdapterPosition].id)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun deleteItemAtPosition(postId: Int) {
        posts.removeAt(posts.indexOfFirst { it.id == postId })
        notifyDataSetChanged()
    }

    class PostViewHolder(
        private val binding: LayoutEntryPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post) {
            binding.textTitle.text = item.title
        }
    }
}

