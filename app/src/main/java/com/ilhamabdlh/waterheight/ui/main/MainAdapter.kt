package com.ilhamabdlh.waterheight.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ilhamabdlh.waterheight.R
import com.ilhamabdlh.waterheight.data.Post
import kotlinx.android.synthetic.main.item_post_each.view.*

class MainAdapter(val posts: List<Post>, val onClickListener: () -> Unit) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post_each, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position], onClickListener)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(post: Post, onClickListener: () -> Unit) {
            if (post.height > 68) {
                view.ivIcon.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_warning))
                view.tvStatus.text = "CHECK"
                view.tvStatus.background = ContextCompat.getDrawable(view.context, R.drawable.bg_red_round)
            } else {
                view.ivIcon.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_check))
                view.tvStatus.text = "OK"
                view.tvStatus.background = ContextCompat.getDrawable(view.context, R.drawable.bg_green_round)
            }
            view.tvTitle.text = post.name
            view.tvAddress.text = post.address
            view.tvLastHeight.text = "Ketinggian: ${post.height} cm"
            view.setOnClickListener { onClickListener() }
        }
    }
}