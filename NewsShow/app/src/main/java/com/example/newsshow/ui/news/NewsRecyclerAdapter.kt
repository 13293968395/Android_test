package com.example.newsshow.ui.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsshow.NewsApplication
import com.example.newsshow.R
import com.example.newsshow.logic.model.NewsModel

class NewsRecyclerAdapter(private val newsList: List<NewsModel.Result.Data>): RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val newsTitle: TextView = view.findViewById(R.id.newsTitle)
        val newsImageView : ImageView = view.findViewById(R.id.newsImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_recycler_item,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val news = newsList[position]
            val intent = Intent(parent.context,NewsContentActivity::class.java).apply {
                putExtra("url",news.url)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            NewsApplication.context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList[position]
        holder.newsTitle.text = news.title
        Glide.with(NewsApplication.context).load(news.picture).into(holder.newsImageView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}