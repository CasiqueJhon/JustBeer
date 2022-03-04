package com.example.justbeer.ui.repository

import com.example.justbeer.model.BeerResultsItem
import kotlinx.coroutines.flow.Flow

interface BeerRepository {

    fun searchBeersByName(beerName: String): Flow <List<BeerResultsItem>>
}