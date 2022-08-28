package com.example.testappksf.domain.usecases

import com.example.testappksf.SOME_ADDRESS
import com.example.testappksf.SOME_SHOP_NAME
import com.example.testappksf.SOME_TEXT
import com.example.testappksf.domain.EMPTY
import com.example.testappksf.domain.data.Place
import com.example.testappksf.repositories.nerwork.response.Datasource
import com.example.testappksf.repositories.nerwork.response.Feature
import com.example.testappksf.repositories.nerwork.response.Properties
import com.example.testappksf.repositories.nerwork.response.RawInfo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapResponseToPlacesUseCaseTest{

    private lateinit var subject: MapResponseToPlacesUseCase

    @Mock
    private lateinit var feature: Feature

    @Mock
    private lateinit var properties: Properties

    @Mock
    private lateinit var datasource: Datasource

    @Mock
    private lateinit var rawInfo: RawInfo

    lateinit var placesList: List<Place>

    @Before
    fun setUp() {
        given(feature.properties).willReturn(properties)
        subject = MapResponseToPlacesUseCase()
    }

    @Test
    fun `givenFeatureAllFieldsOk whenMapResponseCalled thenAddressEqualAddress thenPlaceNameEqualFeatureName`() {
        givenFeature(SOME_TEXT, SOME_SHOP_NAME, SOME_ADDRESS)
        whenMapResponseCalled()
        thenAddressEqual(SOME_ADDRESS)
        thenPlaceNameEqual(SOME_TEXT)
    }

    @Test
    fun `givenFeatureNameNull whenMapResponseCalled thenAddressEqualAddress thenPlaceNameEqualShopName`() {
        givenFeature(null, SOME_SHOP_NAME, SOME_ADDRESS)
        whenMapResponseCalled()
        thenAddressEqual(SOME_ADDRESS)
        thenPlaceNameEqual(SOME_SHOP_NAME)
    }

    @Test
    fun `givenFeatureNameAndShopNameNull whenMapResponseCalled thenAddressEqualAddress thenPlaceNameEqualEmpty`() {
        givenFeature(null, null, SOME_ADDRESS)
        whenMapResponseCalled()
        thenAddressEqual(SOME_ADDRESS)
        thenPlaceNameEqual(EMPTY)
    }

    @Test
    fun `givenFeatureAllFieldsNull whenMapResponseCalled thenAddressEqualAddressEmpty thenPlaceNameEqualEmpty`() {
        givenFeature(null, null, null)
        whenMapResponseCalled()
        thenAddressEqual(EMPTY)
        thenPlaceNameEqual(EMPTY)
    }
    /**
     * GIVEN
     * */

    private fun givenFeature(featureName: String?, shopName: String?, address: String?) {
        given(feature.properties.placeId).willReturn(SOME_TEXT)
        given(feature.properties.addressLineAdditional).willReturn(address)
        given(feature.properties.name).willReturn(featureName)
        given(feature.properties.datasource).willReturn(datasource)
        given(feature.properties.datasource?.raw).willReturn(rawInfo)
        given(feature.properties.datasource?.raw?.shop).willReturn(shopName)
    }

    /**
     * WHEN
     * */

    private fun whenMapResponseCalled() {
        placesList = subject.mapResponse(listOf(feature))
    }

    /**
     * THEN
     * */
    private fun thenPlaceNameEqual(placeName: String?) {
        assertThat(placesList[0].placeName, `is`(placeName))
    }

    private fun thenAddressEqual(address: String?) {
        assertThat(placesList[0].address, `is`(address))
    }
}