package com.example.retofoh.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.retofoh.R
import com.example.retofoh.databinding.ItemMovieBinding
import com.example.retofoh.domain.model.movie
import com.example.retofoh.util.constants.BOOLEAN_FALSE_VALUE
import com.example.retofoh.util.dpToPx

class MovieAdapter(
    private val items: List<movie>,
    private val onItemClickListener: (movie) -> Unit
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, BOOLEAN_FALSE_VALUE)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position % items.size])
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: movie) {
            binding.muvieDescription.text = item.description
            Glide.with(binding.movieImage.context).load(item.image)
                .placeholder(R.drawable.poster_bg)
                .diskCacheStrategy(
                    DiskCacheStrategy.ALL
                ).centerCrop().into(binding.movieImage)
            itemView.setOnClickListener { onItemClickListener(item) }

            val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
            val margin = if (items.size > 1) 1.dpToPx(binding.root.context) else 0
            layoutParams.setMargins(margin, 0, margin, 0)
            binding.root.layoutParams = layoutParams
        }
    }

    override fun getItemCount(): Int {
        return if(items.size <= 1){items.size} else{Int.MAX_VALUE}
    }
}

