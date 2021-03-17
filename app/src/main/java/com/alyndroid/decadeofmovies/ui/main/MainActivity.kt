package com.alyndroid.decadeofmovies.ui.main

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
import com.alyndroid.decadeofmovies.application.MovieApplication
import com.alyndroid.decadeofmovies.pojo.Movie
import com.alyndroid.decadeofmovies.ui.adapters.MovieClickListener
import com.alyndroid.decadeofmovies.ui.adapters.MoviesAdapter
import com.alyndroid.decadeofmovies.ui.details.DetailsBottomSheet
import com.alyndroid.decadeofmovies.util.MOVIE_DETAIL
import com.alyndroid.decadeofmovies.util.SharedPreference
import com.alyndroid.decadeofmovies.util.hide
import com.alyndroid.decadeofmovies.util.show
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).moviesRepository, applicationContext)
    }
    private lateinit var searchView: SearchView
    private var moviesList = listOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSearch()
        customizeSearchBar()
        recyclerView = findViewById(R.id.recyclerview)
        adapter = MoviesAdapter(MovieClickListener {movie->
            DetailsBottomSheet.newInstance(movie)
                .show(supportFragmentManager, MOVIE_DETAIL)
        })
        val searchAdapter = MoviesAdapter(MovieClickListener {movie->
            DetailsBottomSheet.newInstance(movie)
                .show(supportFragmentManager, MOVIE_DETAIL)
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter



        if (SharedPreference(this).getValueBoolien(getString(R.string.isFirstTime), true)) {
            movieViewModel.parseJsonToRoomDB()
            SharedPreference(this).save(getString(R.string.isFirstTime), false)
        }



        movieViewModel.searchResults.observe(this, Observer { movies ->
            if(movies.isEmpty()){
                empty_image.show()
                empty_message_tv.show()
                recyclerView.hide()
            } else {
                recyclerView.show()
                empty_image.hide()
                empty_message_tv.hide()
                movies.let {
                    val searchMoviesResult = movies.asReversed()
                        .groupBy {
                            it.year
                        }.flatMap {
                            // we gonna sort the rating of an entity and
                            // limit the results to get the top 5
                            var list = it.value.sortedByDescending { movie ->
                                movie.rating
                            }.toMutableList<Any>()
                            if (list.size > 5)
                                list = list.subList(0, 5)
                            // append the year value
                            list.add(0, it.key)
                            list
                        }
                    searchAdapter.submitList(searchMoviesResult)
                    recyclerView.adapter = searchAdapter
                    searchAdapter.notifyDataSetChanged()
                }
            }
        })

        movieViewModel.allMovies.observe(this, Observer { movies ->
            movies.let {
                moviesList = it
                (adapter.submitList(moviesList))
            }
        })
    }

    private fun initSearch() {
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

    private fun customizeSearchBar() {
        (searchView.findViewById(androidx.appcompat.R.id.search_src_text) as TextView).apply {
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.gray_light))
        }
    }
}