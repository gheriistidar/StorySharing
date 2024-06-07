package com.dicoding.picodiploma.dicodingstory.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.dicodingstory.R
import com.dicoding.picodiploma.dicodingstory.data.api.response.ListStoryItem
import com.dicoding.picodiploma.dicodingstory.databinding.ItemStoryBinding
import com.dicoding.picodiploma.dicodingstory.view.storydetail.StoryDetailActivity

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: ListStoryItem) {
            binding.tvDetailName.text = story.name
            binding.tvDetailDescription.text = story.description
            Glide.with(binding.root).load(story.photoUrl).centerCrop()
                .placeholder(R.drawable.img_placeholder).error(R.drawable.img_placeholder)
                .into(binding.ivDetailPhoto)

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, StoryDetailActivity::class.java).apply {
                    putExtra("story", story)
                }

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as AppCompatActivity,
                        Pair(binding.ivDetailPhoto, "profile"),
                        Pair(binding.tvDetailName, "name"),
                        Pair(binding.tvDetailDescription, "description")
                    )
                context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}