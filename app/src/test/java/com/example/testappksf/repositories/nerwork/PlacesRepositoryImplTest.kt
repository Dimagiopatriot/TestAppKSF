package com.example.testappksf.repositories.nerwork

import com.example.testappksf.*
import com.example.testappksf.repositories.nerwork.response.ApiResponse
import com.example.testappksf.repositories.nerwork.response.Feature
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PlacesRepositoryImplTest {

    private lateinit var subject: PlacesRepository

    @Mock
    lateinit var restApiManager: RestApiManager

    @Mock
    lateinit var placesAPI: PlacesAPI

    @Mock
    lateinit var apiResponse: Response<ApiResponse>

    private lateinit var result: List<Feature>

    @Before
    fun setUp() {
        givenApiKey()
        givenGetPlacesLimit()
        givenRestApi()
        subject = PlacesRepositoryImpl(restApiManager)
    }


    @Test
    fun `givenPlacesApi givenApiResponse whenGetPlacesByCoordinates thenResultEqualToList`() =
        runBlocking {
            givenPlacesApi()
            givenApiResponse(true, SOME_API_RESPONSE)
            whenGetPlacesByCoordinates()
            thenResultEqualTo(SOME_FEATURE_LIST)

        }

    @Test
    fun `givenPlacesApi givenApiResponseNotSuccess whenGetPlacesByCoordinates thenResultEqualToEmpty`() =
        runBlocking() {
            givenPlacesApi()
            givenApiResponse(false, SOME_API_RESPONSE)
            whenGetPlacesByCoordinates()
            thenResultEqualTo(listOf())
        }

    @Test
    fun `givenPlacesApi givenApiResponseBodyNull whenGetPlacesByCoordinates thenResultEqualToEmpty`() =
        runBlocking() {
            givenPlacesApi()
            givenApiResponse(true, null)
            whenGetPlacesByCoordinates()
            thenResultEqualTo(listOf())
        }

    /**
     * GIVEN
     * */
    private fun givenRestApi() {
        given(restApiManager.buildFromURL()).willReturn(placesAPI)
    }

    private suspend fun givenPlacesApi() {
        given(
            placesAPI.getPlaces(
                anyString(),
                anyInt(),
                anyInt(),
                anyString(),
                anyString()
            )
        ).willReturn(
            apiResponse
        )
    }

    private fun givenGetPlacesLimit() {
        given(restApiManager.getPlacesLimit()).willReturn(SOME_INT.toString())
    }

    private fun givenApiKey() {
        given(restApiManager.getApiKey()).willReturn(SOME_TEXT)
    }

    private fun givenApiResponse(isSuccessful: Boolean, apiResponseBody: ApiResponse?) {
        given(apiResponse.isSuccessful).willReturn(isSuccessful)
        given(apiResponse.body()).willReturn(apiResponseBody)
    }

    /**
     * WHEN
     * */
    private suspend fun whenGetPlacesByCoordinates() {
        result = subject.getPlacesByCoordinates(SOME_TEXT, SOME_DOUBLE, SOME_DOUBLE, SOME_INT)
    }

    /**
     * THEN
     * */

    private fun thenResultEqualTo(expected: List<Feature>) {
        assertThat(result, equalTo(expected))
    }
}