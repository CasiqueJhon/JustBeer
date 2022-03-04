package com.example.justbeer.ui.repository

import com.example.justbeer.data.ApiService
import com.example.justbeer.model.BeerResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(private val apiService: ApiService) : BeerRepository {

    override fun searchBeersByName(beerName: String): Flow<List<BeerResultsItem>> = flow {
        try {
            val response = apiService.getBeers(PAGE_NUMBER, PAGE_SIZE, beerName)
            emit(response)
        }catch (e : Throwable){
            e.stackTrace
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val PAGE_NUMBER = 1
        private const val PAGE_SIZE = 30
    }
}