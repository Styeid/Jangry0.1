package com.jufaja.jangry01

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jufaja.jangry01.models.Post
import kotlinx.android.synthetic.main.item_post.view.*

class Postsadapter (val context: Context, val posts: List<Post>) :
    RecyclerView.Adapter<Postsadapter.Viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return Viewholder(view)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(posts[position])
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            itemView.tvUsername.text = post.user?.username
            itemView.tvDescreption.text = post.omschrijving
            Glide.with(context).load(post.imageUrl).into(itemView.ivPost)
            itemView.tvRelativeTime.text = DateUtils.getRelativeTimeSpanString(post.datumTijdMs)
        }
    }
}