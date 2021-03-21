package com.alyndroid.decadeofmovies.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.alyndroid.decadeofmovies.R
import com.alyndroid.decadeofmovies.domain.model.Movie
import com.alyndroid.decadeofmovies.presentation.adapters.ImagesAdapter
import com.alyndroid.decadeofmovies.presentation.states.DetailsUiState
import com.alyndroid.decadeofmovies.presentation.viewModels.DetailsViewModel
import com.alyndroid.decadeofmovies.util.MOVIE_DETAIL_Extra
import com.alyndroid.decadeofmovies.util.hide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var movie: Movie
    private val galleryAdapter = ImagesAdapter()

    private val viewModel by viewModels<DetailsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        movie = intent.getParcelableExtra(MOVIE_DETAIL_Extra)!!
        initImagesRecycler()
        bindViews(movie)

        viewModel.search(movie.title)
        lifecycleScope.launchWhenStarted {
            viewModel.detailsUiState.collect { detailsUiState ->
                when (detailsUiState) {
                    is DetailsUiState.Success -> {
                        galleryAdapter.submitData(detailsUiState.imageWrapperResponse.photos.photo.map { p -> p.toUrl() })
                        animation_view?.isVisible = false
                    }
                    is DetailsUiState.Error -> {
                        Toast.makeText(
                            this@DetailsActivity,
                            detailsUiState.errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                        animation_view?.isVisible = false
                    }
                    is DetailsUiState.Loading -> {
                        animation_view?.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun bindViews(movie: Movie) {

        with(movie) {
            if (cast.isEmpty()) {
                cast_details_tv.hide()
                cast_details_title_tv.hide()
            }
            if (genres.isEmpty()) {
                genre_details_tv.hide()
            }
            name_details_tv?.text = title
            year_details_tv?.text = year.toString()
            cast_details_tv?.text = cast.joinToString(separator = ", ")
            genre_details_tv?.text = genres.joinToString(separator = ", ")
            ratingBar?.rating = rating.toFloat()
            ratingBar?.isEnabled = false
        }

    }

    private fun initImagesRecycler() {
        gallery_details_rv?.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        gallery_details_rv?.itemAnimator = DefaultItemAnimator()
        gallery_details_rv?.setHasFixedSize(true)
        gallery_details_rv.isNestedScrollingEnabled = true
        gallery_details_rv?.adapter = galleryAdapter
    }
}