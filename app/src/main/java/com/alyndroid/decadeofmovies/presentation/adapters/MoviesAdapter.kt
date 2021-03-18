package com.alyndroid.decadeofmovies.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alyndroid.decadeofmovies.R
import com.alyndroid.decadeofmovies.domain.model.Movie
import com.alyndroid.decadeofmovies.util.TYPE_MOVIE
import com.alyndroid.decadeofmovies.util.TYPE_YEAR

class MoviesAdapter(private val movieClickListener:MovieClickListener) : ListAdapter<Any, RecyclerView.ViewHolder>(MovieComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_MOVIE -> MovieViewHolder.create(parent)
            TYPE_YEAR -> YearViewHolder.create(parent)
            else -> MovieViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            TYPE_MOVIE -> {
                val current = getItem(position) as Movie
                (holder as MovieViewHolder).bind(current)
                holder.movieCardView.setOnClickListener {
                    movieClickListener.onClick(current)
                }
            }

            TYPE_YEAR -> {
                val current = getItem(position) as Int
                (holder as YearViewHolder).bind(current.toString())
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position) is Int) {
            return TYPE_YEAR
        } else if (getItem(position) is Movie) {
            return TYPE_MOVIE
        }
        return -1
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieItemTitle: TextView = itemView.findViewById(R.id.movie_item_title)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        private val movieItemYear: TextView = itemView.findViewById(R.id.movie_item_year)
        val movieCardView: CardView = itemView.findViewById(R.id.movie_card_view)

        fun bind(movie: Movie?) {
            movieItemTitle.text = movie!!.title
            movieItemYear.text = movie.year.toString()
            ratingBar.rating = movie.rating.toFloat()
        }

        companion object {
            fun create(parent: ViewGroup): MovieViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.movie_item, parent, false)
                return MovieViewHolder(view)
            }
        }
    }

    class YearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val yearItemView: TextView = itemView.findViewById(R.id.tvYear)

        fun bind(text: String?) {
            yearItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): YearViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.year_item, parent, false)
                return YearViewHolder(view)
            }
        }
    }

    class MovieComparator : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is Movie && newItem is Movie) {
                oldItem.title == newItem.title
            } else if (oldItem is Int && newItem is Int)
                oldItem == newItem
            else
                false
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is Movie && newItem is Movie) {
                oldItem as Movie == newItem as Movie
            } else if (oldItem is Int && newItem is Int)
                oldItem as Int == newItem as Int
            else
                false
        }
    }
}

class MovieClickListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}