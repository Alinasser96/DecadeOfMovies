package com.alyndroid.decadeofmovies.ui.main

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alyndroid.decadeofmovies.R
import com.alyndroid.decadeofmovies.application.MovieApplication
import com.alyndroid.decadeofmovies.util.SharedPreference
import com.alyndroid.decadeofmovies.ui.adapters.MoviesAdapter

class MainActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).repository, applicationContext)
    }
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = MoviesAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        if (SharedPreference(this).getValueBoolien(getString(R.string.isFirstTime), true)) {
            movieViewModel.parseJsonToRoomDB()
            SharedPreference(this).save(getString(R.string.isFirstTime), false)
        }



        movieViewModel.searchResults.observe(this, Observer { movies->
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
                adapter.submitList(searchMoviesResult)
                adapter.notifyDataSetChanged()
            }

        })

        movieViewModel.allMovies.observe(this, Observer { movies ->
            movies.let { (adapter.submitList(it)) }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        val searchItem = menu!!.findItem(R.id.search_bar)
        searchView = searchItem.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                movieViewModel.search(query = newText)
                return true
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                movieViewModel.search(query = query)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}