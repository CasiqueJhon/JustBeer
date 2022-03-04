package com.example.justbeer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.justbeer.ui.repository.BeerRepositoryImpl
import com.example.justbeer.model.BeerResultsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(private val beerRepository: BeerRepositoryImpl) : ViewModel() {

    private val searchBeersChannel = ConflatedBroadcastChannel<String>()

    private var beersSearch = searchBeersChannel
        .asFlow()
        .debounce(800)
        .flatMapLatest {
            beerRepository.searchBeersByName(it)
        }
        .catch {
            _error.value = it.localizedMessage
        }.asLiveData()

    val beers: LiveData<List<BeerResultsItem>>
    get() = beersSearch

    val error: LiveData<String>
    get() = _error

    private var _error = MutableLiveData<String>()

    init {
        searchBeersChannel.offer("malt")
    }

    fun search(query: String) {
        searchBeersChannel.offer(query)
    }

}