package com.example.newsapp.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.Article
import com.example.newsapp.R
import com.example.newsapp.databinding.ArticleListItemBinding

class NewsAdapter(val a: Activity, val articles: ArrayList<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: ArticleListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val b = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(b)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        holder.binding.articleTv.text = articles[position].title
        Glide.with(holder.binding.articleImg.context)
            .load(articles[position].urlToImage)
            .error(R.drawable.ic_broken)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.articleImg)

        val url = articles[position].url

        holder.binding.articleContainer.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            a.startActivity(intent)
        }

        holder.binding.sharedFab.setOnClickListener {
            ShareCompat
                .IntentBuilder(a)
                .setType("text/plain")
                .setText(url)
                .startChooser()
        }
    }

    override fun getItemCount(): Int = articles.size


}