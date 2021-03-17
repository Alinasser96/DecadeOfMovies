package com.alyndroid.decadeofmovies.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.alyndroid.decadeofmovies.R
import com.alyndroid.decadeofmovies.application.MovieApplication
import com.alyndroid.decadeofmovies.pojo.Movie
import com.alyndroid.decadeofmovies.ui.adapters.ImagesAdapter
import com.alyndroid.decadeofmovies.ui.main.MovieViewModel
import com.alyndroid.decadeofmovies.ui.main.MovieViewModelFactory
import com.alyndroid.decadeofmovies.util.MOVIE_KEY
import com.alyndroid.decadeofmovies.util.hide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_details_bottom_sheet.*

class DetailsBottomSheet : BottomSheetDialogFragment() {
    private lateinit var movie: Movie
    private val galleryAdapter = ImagesAdapter()
    val viewModel by viewModels<DetailsViewModel>()
    companion object {
        fun newInstance(movie: Movie): DetailsBottomSheet {
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_KEY, movie)
            with(DetailsBottomSheet()) {
                arguments = bundle
                return this
            }

        }
    }

    private fun initImagesRecycler() {
        gallery_details_rv?.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        gallery_details_rv?.itemAnimator = DefaultItemAnimator()
        gallery_details_rv?.setHasFixedSize(true)
        gallery_details_rv.isNestedScrollingEnabled = true
        gallery_details_rv?.adapter = galleryAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogStyle)
        movie = arguments!![MOVIE_KEY] as Movie
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.movie_details_bottom_sheet, container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImagesRecycler()
        bindViews(movie)
        viewModel.search(movie.title)
        viewModel.searchResults.observe(requireActivity(), Observer {
            galleryAdapter.submitData(it.photos.photo.map { p->p.toUrl()})
        })

        viewModel.loading.observe(requireActivity(), Observer {
            animation_view?.isVisible = it
        })
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
}