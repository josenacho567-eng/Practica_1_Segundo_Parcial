package com.example.practica_1_segundo_parcial.feature.trivia.data.remote

import com.example.practica_1_segundo_parcial.data.remote.RetrofitClient

object TriviaApiClient {
    val service: TriviaApiService get() = RetrofitClient.triviaApi
}
