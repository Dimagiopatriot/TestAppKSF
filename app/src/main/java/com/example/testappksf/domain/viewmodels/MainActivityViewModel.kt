package com.example.testappksf.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testappksf.domain.data.Place
import com.example.testappksf.domain.usecases.GetPlacesApiResponseUseCase
import com.example.testappksf.domain.usecases.MapResponseToPlacesUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivityViewModel constructor(
    private val getPlacesApiResponseUseCase: GetPlacesApiResponseUseCase,
    private val mapResponseToPlacesUseCase: MapResponseToPlacesUseCase
) : ViewModel() {

    val places = MutableLiveData<MutableList<Place>>()
    val hasStartLoader = MutableLiveData(true)
    val hasShowErrorMessage = MutableLiveData(false)

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        hasStartLoader.postValue(false)
        hasShowErrorMessage.postValue(true)
    }

    fun getPlaces(
        offset: Int,
        coordinates: Pair<Double, Double>,
    ) {
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            val placesListAsync = async {
                merge(
                    flowOf(
                        getPlacesApiResponseUseCase.getResponse(
                            offset,
                            "commercial.food_and_drink",
                            coordinates.first,
                            coordinates.second
                        )
                    ),
                    flowOf(
                        getPlacesApiResponseUseCase.getResponse(
                            offset,
                            "commercial.supermarket",
                            coordinates.first,
                            coordinates.second
                        )
                    )
                )
                    .map { mapResponseToPlacesUseCase.mapResponse(it) }
                    .onCompletion { hasStartLoader.postValue(false) }
                    .toList()
                    .flatten()
            }

            val placeList = placesListAsync.await().toMutableList()
            places.value?.let {
                it.addAll(placeList)
                places.postValue(places.value)
            } ?: places.postValue(placeList)
            hasShowErrorMessage.postValue(places.value.isNullOrEmpty())
        }
    }
}