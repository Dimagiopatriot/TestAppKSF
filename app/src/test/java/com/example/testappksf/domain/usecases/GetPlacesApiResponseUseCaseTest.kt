package com.example.testappksf.domain.usecases

import com.example.testappksf.SOME_DOUBLE
import com.example.testappksf.SOME_INT
import com.example.testappksf.SOME_TEXT
import com.example.testappksf.repositories.nerwork.PlacesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPlacesApiResponseUseCaseTest {

    @Mock
    lateinit var placesRepository: PlacesRepository

    lateinit var subject: GetPlacesApiResponseUseCase

    @Before
    fun setUp() {
        subject = GetPlacesApiResponseUseCase(placesRepository)
    }

    @Test
    fun `whenGetResponseCalled thenGetPlacesByCoordinatesCalled`()= runBlocking  {
        whenGetResponseCalled()
        thenGetPlacesByCoordinatesCalled()
    }

    /**
     * WHEN
     * */
    private suspend fun whenGetResponseCalled() {
        subject.getResponse(SOME_INT, SOME_TEXT, SOME_DOUBLE, SOME_DOUBLE)
    }

    /**
     * THEN
     * */
    private suspend fun thenGetPlacesByCoordinatesCalled() {
        verify(placesRepository).getPlacesByCoordinates(
            SOME_TEXT,
            SOME_DOUBLE,
            SOME_DOUBLE,
            SOME_INT
        )
    }
}