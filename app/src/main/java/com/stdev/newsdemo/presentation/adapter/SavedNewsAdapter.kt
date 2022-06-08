package com.stdev.newsdemo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stdev.newsdemo.data.model.Article
import com.stdev.newsdemo.databinding.SavedNewsListItemBinding

class SavedNewsAdapter : RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>() {


    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,callback)

    inner class SavedNewsViewHolder(val binding : SavedNewsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(article: Article){
            binding.savedNewsListCategory.text = "${ article.source?.name }"
            binding.savedNewsListTitle.text = article.title
            Glide.with(binding.savedNewsListImage.context)
                .load(article.urlToImage)
                .into(binding.savedNewsListImage)
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    private var onItemClickListener : ((Article?)->Unit)? = null

    fun setOnItemClickListener(listener : (Article?)->Unit){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val binding = SavedNewsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SavedNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bindData(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}