package com.alyndroid.decadeofmovies.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alyndroid.decadeofmovies.R
import com.alyndroid.decadeofmovies.application.MovieApplication
import com.alyndroid.decadeofmovies.helper.SharedPreference
import com.alyndroid.decadeofmovies.ui.adapters.MoviesAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val wordViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).repository, applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = MoviesAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        if (SharedPreference(this).getValueBoolien(getString(R.string.isFirstTime), true)){
            wordViewModel.parseJsonToRoomDB()
            SharedPreference(this).save(getString(R.string.isFirstTime), false)
        }


        wordViewModel.allMovies.observe(this, Observer { movies ->
            movies.let { (adapter.submitList(it)) }
        })
    }
}