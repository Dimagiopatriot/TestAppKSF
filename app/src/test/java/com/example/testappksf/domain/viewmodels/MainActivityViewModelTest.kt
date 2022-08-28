package com.example.testappksf.domain.viewmodels

import com.example.testappksf.*
import com.example.testappksf.domain.data.Place
import com.example.testappksf.domain.usecases.GetPlacesApiResponseUseCase
import com.example.testappksf.domain.usecases.MapResponseToPlacesUseCase
import com.example.testappksf.repositories.nerwork.response.Feature
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MainActivityViewModelTest {

    @Mock
    lateinit var getPlacesApiResponseUseCase: GetPlacesApiResponseUseCase

    @Mock
    lateinit var mapResponseToPlacesUseCase: MapResponseToPlacesUseCase

    private lateinit var subject: MainActivityViewModel

    @Before
    fun setUp() {

        subject = MainActivityViewModel(getPlacesApiResponseUseCase, mapResponseToPlacesUseCase)
    }

    @Test
    fun `givenGetResponse givenMapResponse whenGetPlacesCalled thenMapResponseCalled`() =
        runBlocking {
            givenGetResponse(SOME_FEATURE_LIST)
            givenMapResponse(SOME_PLACE_LIST)
            onWhenGetPlacesCalled { thenMapResponseCalled(SOME_FEATURE_LIST) }
        }

    @Test
    fun `givenGetResponse givenMapResponse whenGetPlacesCalled thenHasStartLoaderFalse`() =
        runBlocking {
            givenGetResponse(SOME_FEATURE_LIST)
            givenMapResponse(SOME_PLACE_LIST)
            onWhenGetPlacesCalled { thenHasStartLoader(false) }
        }

    @Test
    fun `givenGetResponse givenMapResponse whenGetPlacesCalled thenHasShowErrorMessageFalse`() =
        runBlocking {
            givenGetResponse(SOME_FEATURE_LIST)
            givenMapResponse(SOME_PLACE_LIST)
            onWhenGetPlacesCalled { thenHasShowErrorMessage(false) }
        }

    @Test
    fun `givenGetResponse givenMapResponseNull whenGetPlacesCalled thenHasShowErrorMessageTrue`() =
        runBlocking {
            givenGetResponse(SOME_FEATURE_LIST)
            givenMapResponse(null)
            onWhenGetPlacesCalled { thenHasShowErrorMessage(true) }
        }


    @Test
    fun `givenGetResponse givenMapResponseEmpty whenGetPlacesCalled thenHasShowErrorMessageTrue`() =
        runBlocking {
            givenGetResponse(SOME_FEATURE_LIST)
            givenMapResponse(listOf())
            onWhenGetPlacesCalled { thenHasShowErrorMessage(true) }
        }

    @Test
    fun `givenGetResponseThrowable givenMapResponseEmpty whenGetPlacesCalled thenHasStartLoaderFalse thenHasShowErrorMessageTrue`() =
        runBlocking {
            givenGetResponseThrowable()
            givenMapResponse(listOf())
            onWhenGetPlacesCalled {
                thenHasStartLoader(false)
                thenHasShowErrorMessage(true)
            }
        }

    @Test
    fun `givenGetResponse givenMapResponseThrowable whenGetPlacesCalled thenHasStartLoaderFalse thenHasShowErrorMessageTrue`() = runBlocking {
        givenGetResponse(SOME_FEATURE_LIST)
        givenMapResponseThrowable()
        onWhenGetPlacesCalled {
            thenHasStartLoader(false)
            thenHasShowErrorMessage(true)
        }
    }

    /**
     * GIVEN
     * */
    private suspend fun givenGetResponse(features: List<Feature>?) {
        given(getPlacesApiResponseUseCase.getResponse(SOME_INT, SOME_TEXT)).willReturn(features)
    }

    private suspend fun givenGetResponseThrowable() {
        given(
            getPlacesApiResponseUseCase.getResponse(
                SOME_INT,
                SOME_TEXT
            )
        ).willAnswer { throw Exception() }
    }

    private fun givenMapResponse(placeList: List<Place>?) {
        given(mapResponseToPlacesUseCase.mapResponse(SOME_FEATURE_LIST)).willReturn(placeList)
    }

    private fun givenMapResponseThrowable() {
        given(mapResponseToPlacesUseCase.mapResponse(SOME_FEATURE_LIST)).willAnswer { throw Exception() }
    }

    /**
     * WHEN
     * */
    private fun whenGetPlacesCalled() {
        subject.getPlaces(
            0,
            Pair(SOME_DOUBLE, SOME_DOUBLE)
        )
    }

    /**
     * THEN
     * */
    private fun thenMapResponseCalled(featureList: List<Feature>) {
        verify(mapResponseToPlacesUseCase, times(1)).mapResponse(featureList)
    }

    private fun thenHasStartLoader(hasLoader: Boolean) {
        verify(subject.hasStartLoader, times(1)).postValue(hasLoader)
    }

    private fun thenHasShowErrorMessage(hasShowErrorMessage: Boolean) {
        verify(subject.hasShowErrorMessage, times(1)).postValue(hasShowErrorMessage)
    }

    private fun onWhenGetPlacesCalled(onThenCallback: () -> Unit) = runBlocking {
        launch {
            whenGetPlacesCalled()
        }.invokeOnCompletion {
            onThenCallback()
        }.dispose()
    }

}