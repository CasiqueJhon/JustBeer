package com.example.justbeer.data

import com.example.justbeer.model.BeerResultsItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("beer_name") beerName: String
    ): List<BeerResultsItem>
}