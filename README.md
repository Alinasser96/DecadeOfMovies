## Decade of Movies

The past decade held a lot of movies, some left a mark and some were just a set of 24-60 pictures per second.This app is a Master - Detail Application​ to showcase those movies and the signature they left behind.

## Challenge description
- Load movies list from a JSON file.
- Ability to search movies by name .
- Categorize the movies search results by year and each year category has the top 5 rated movies within.
- Show movies details with the following details :-
    - Movie Title.
    - Movie Year.
    - Movie Genres (if any).
    - Movie Cast (if any).
    - A two column list of pictures fetched from flickr that matches the movie title as the search
      query.
- Using Flicker api to retrieve photos that matches the movie title in a 2 column grid 

## Screenshot
<img src="https://github.com/Alinasser96/DecadeOfMovies/blob/master/screenshots/1.png"></a><img src="https://github.com/Alinasser96/DecadeOfMovies/blob/master/screenshots/2.png"></a>
<img src="https://github.com/Alinasser96/DecadeOfMovies/blob/master/screenshots/3.png"></a>
<img src="https://github.com/Alinasser96/DecadeOfMovies/blob/master/screenshots/4.png"></a>
<img src="https://github.com/Alinasser96/DecadeOfMovies/blob/master/screenshots/5.png"></a>

## Specifications

- Caching movies from JSON file to Room DB to optimize loading time.
- Clean architecture.
- comments.
- searching algorithm.
- UI test.
- Unit test.

## Languages, libraries and tools used

 * [Kotlin](https://kotlinlang.org/)
 * [androidX libraries](https://developer.android.com/jetpack/androidx)
 * [Android LifeCycle](https://developer.android.com/topic/libraries/architecture)
 * [coroutines](https://github.com/Kotlin/kotlinx.coroutines)
 * [Retrofit2]
 * [Room]
 * [Dagger - Hilt](https://dagger.dev/hilt/)
 * [Glide](https://bumptech.github.io/glide/)
 * [Lottie]
 * [Android Test Support Library]
 * [Mockito Kotlin](https://github.com/nhaarman/mockito-kotlin/)
 
 
## Requirements
- min SDK 19

## Installation

- Just clone the app and import to Android Studio.
``git clone https://github.com/Alinasser96/DecadeOfMovies.git``

## Usage

- For testing the app there is an APK build [HERE!](https://github.com/Alinasser96/DecadeOfMovies/raw/master/MoD.apk) or in  the repo main page that you can directly download and install.



## clean Architecture
* In this project I'm using [clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
and also ``MVVM`` as an application architecture adopted from the architecture blueprints sample.

<img src="https://res.cloudinary.com/practicaldev/image/fetch/s--c0f9PFvt--/c_limit%2Cf_auto%2Cfl_progressive%2Cq_auto%2Cw_880/http://wahibhaq.github.io/img/blog/posts/summary-thoughts-clean-architecture-mvp/clean-architecture-ring-diagram.png"></a>

## searching
* i'm using Full Text Search (FTS) algorithm in this project, also i have migrated it to Room DataBase to use it instead of regular SQL search
, it is faster and more reliable.

## sorting
* i've tried sorting algorithm like selection sort, insertion sort and quick sort
i have ended with that quick sort is the fastest one and by the magic of luck kotlin by default uses quick sort in sort Ext Fun.

## Implementation

* Using Dagger Hilt for dependency injection that will make testing easier and our make code
cleaner and more readable.
* Using Retrofit library to handle the APIs stuff.
* Using Room for caching movies.
* Using StateFlow for handling ui states.


## License
MIT License
```
Copyright (c) [2021] [Aly Hamalawey]
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

