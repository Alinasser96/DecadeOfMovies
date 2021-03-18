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
import com.alyndroid.decadeofmovies.util.SharedPreference
import com.alyndroid.decadeofmovies.util.hide
import com.alyndroid.decadeofmovies.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var moviesList = listOf<Movie>()

    val movieViewModel by viewModels<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSearchView()
        recyclerView = findViewById(R.id.recyclerview)
        adapter = MoviesAdapter(MovieClickListener { movie ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("Extra_Movie", movie)
            startActivity(intent)
        })
        val searchAdapter = MoviesAdapter(MovieClickListener { movie ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("Extra_Movie", movie)
            startActivity(intent)
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter



        if (SharedPreference(this).getValueBoolien(getString(R.string.isFirstTime), true)) {
            movieViewModel.insertJsonToRoomDB()
            SharedPreference(this).save(getString(R.string.isFirstTime), false)
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
}