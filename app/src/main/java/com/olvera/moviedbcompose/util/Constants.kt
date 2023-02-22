package com.olvera.moviedbcompose.util

class Constants {

    companion object {
        const val API_KEY = "38ef64108127c75e24590c44c9513a57"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val IMAGE_W500 = "w500"

        const val QUERY_API_KEY = "api_key"
        const val PATH_MOVIE_ID = "movie_id"

        const val ARG_MOVIE_ID = "MOVIE_ID"
        const val MOVIE_DETAIL = "movie/{$ARG_MOVIE_ID}/detail"

        const val MOVIE_DETAILS_BASE_URL = "https://www.youtube.com/watch?v="

        fun buildYouTubeThumbnailURL(key: String): String {
            return "https://img.youtube.com/vi/$key/0.jpg"
        }

        fun buildYoutubeURL(key: String): String {
            return "$MOVIE_DETAILS_BASE_URL$key"
        }


    }
}