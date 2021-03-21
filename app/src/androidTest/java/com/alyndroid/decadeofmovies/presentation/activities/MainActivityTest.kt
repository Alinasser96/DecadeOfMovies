package com.alyndroid.decadeofmovies.presentation.activities

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.alyndroid.decadeofmovies.R
import com.alyndroid.decadeofmovies.domain.model.Movie
import com.alyndroid.decadeofmovies.presentation.adapters.MoviesAdapter
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    val testMovie = Movie(listOf("Zac Efron",
        "Leslie Mann",
        "Thomas Lennon",
        "Matthew Perry",
        "Melora Hardin",
        "Michelle Trachtenberg",
        "Sterling Knight"), listOf("Comedy",
        "Teen"), 1, "17 Again", 2009)

    @Test
    fun whenLaunch_RecyclerViewMustBeVisible() {
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun whenRecyclerViewItemClicked_mustOpenDetailsActivity() {
        Thread.sleep(1500)
        onView(withId(R.id.recyclerview)).perform(actionOnItemAtPosition<MoviesAdapter.MovieViewHolder>(0, click()))
        onView(withId(R.id.name_details_tv)).check(matches(withText("'Moms' Night Out")))
    }

    @Test
    fun whenPressBackOnDetailsActivity_recyclerViewMustDisplay() {
        Thread.sleep(1500)
        onView(withId(R.id.recyclerview)).perform(actionOnItemAtPosition<MoviesAdapter.MovieViewHolder>(0, click()))
        onView(withId(R.id.name_details_tv)).check(matches(withText("'Moms' Night Out")))
        pressBack()
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun whenSearchAboutText_mustUpdateRecyclerView() {
        onView(withId(R.id.svMovies)).perform(click())
        onView(withId(R.id.svMovies)).perform(typeSearchViewText(testMovie.title))
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun whenClickSearchResult_mustOpenDetailsActivity() {
        onView(withId(R.id.svMovies)).perform(click())
        onView(withId(R.id.svMovies)).perform(typeSearchViewText(testMovie.title))
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerview)).perform(actionOnItemAtPosition<MoviesAdapter.MovieViewHolder>(1, click()))
        onView(withId(R.id.name_details_tv)).check(matches(withText(testMovie.title)))
    }
}

fun typeSearchViewText(text: String?): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            //Ensure that only apply if it is a SearchView and if it is visible.
            return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
        }

        override fun getDescription(): String {
            return "Change view text"
        }

        override fun perform(uiController: UiController?, view: View) {
            (view as SearchView).setQuery(text, false)
        }
    }
}