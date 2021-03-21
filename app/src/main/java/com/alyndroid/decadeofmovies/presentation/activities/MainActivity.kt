package com.alyndroid.decadeofmovies.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alyndroid.decadeofmovies.R
import com.alyndroid.decadeofmovies.domain.model.Movie
import com.alyndroid.decadeofmovies.presentation.adapters.MovieClickListener
import com.alyndroid.decadeofmovies.presentation.adapters.MoviesAdapter
import com.alyndroid.decadeofmovies.presentation.viewModels.MovieViewModel
import com.alyndroid.decadeofmovies.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private lateinit var adapter: MoviesAdapter
    private lateinit var searchAdapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var moviesList = listOf<Movie>()

    val movieViewModel by viewModels<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieClickListener = MovieClickListener{movie->
            startDetailsActivity(movie)
        }
        initSearchView()
        recyclerView = findViewById(R.id.recyclerview)
        adapter = MoviesAdapter(movieClickListener)
        searchAdapter = MoviesAdapter(movieClickListener)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter



        if (SharedPreference(this).getValueBoolean(FIRST_TIME, true)) { //check if it isFirstTime or not to parse just one time
            movieViewModel.insertJsonToRoomDB()
            SharedPreference(this).save(FIRST_TIME, false)
        }

        movieViewModel.searchResults.observe(this, Observer { movies ->
            if (movies.isEmpty()) {
                empty_image.show()
                empty_message_tv.show()
                recyclerView.hide()
            } else {
                recyclerView.show()
                empty_image.hide()
                empty_message_tv.hide()
                searchAdapter.submitList(movies)
                recyclerView.adapter = searchAdapter
                searchAdapter.notifyDataSetChanged()
            }
        })

        movieViewModel.allMovies.observe(this, Observer { movies ->
            movies.let {
                moviesList = it
                (adapter.submitList(moviesList))
                recyclerView.adapter = adapter
            }
        })
    }

    private fun initSearchView() {
        searchView = findViewById(R.id.svMovies)
        (searchView.findViewById(androidx.appcompat.R.id.search_src_text) as TextView).apply {
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.gray_light))
            setHintTextColor(ContextCompat.getColor(this@MainActivity, R.color.gray_text_disabled))
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    movieViewModel.search(query = newText)
                } else {
                    adapter.submitList(moviesList)
                    recyclerView.adapter = adapter
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                movieViewModel.search(query = query)
                return true
            }
        })
    }

    private fun startDetailsActivity(movie: Movie){
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(MOVIE_DETAIL_Extra, movie)
        startActivity(intent)
    }
}