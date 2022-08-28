package com.example.testappksf.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.testappksf.domain.ZERO
import com.example.testappksf.domain.getProperty
import com.example.testappksf.domain.viewmodels.MainActivityViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var coordinates: Pair<Double, Double>
    private var offset = ZERO.toInt()

    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when (true) {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION], permissions[Manifest.permission.ACCESS_COARSE_LOCATION] -> getLocation()
            else -> locationMalfunction()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        coordinates =
            Pair(getProperty("default_lat").toDouble(), getProperty("default_lon").toDouble())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestLocation()
        setContent {
            RenderUI()
        }
    }

    @Composable
    fun RenderUI() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            mainActivityViewModel.hasShowErrorMessage.observeAsState().value?.let {
                ErrorPlaceMessage(
                    errorText = "Unfortunately, couldn't load places",
                    isVisible = it
                )
            }
            mainActivityViewModel.hasStartLoader.observeAsState().value?.let {
                LoadingAnimation(isVisible = it && mainActivityViewModel.places.value.isNullOrEmpty())
            }
            val listState = rememberLazyListState()
            mainActivityViewModel.places.observeAsState().value?.let {
                PlacesList(
                    it,
                    listState,
                    mainActivityViewModel.hasStartLoader.value ?: false
                ) {
                    offset = ZERO.toInt()
                    mainActivityViewModel.places.value = mutableListOf()
                    mainActivityViewModel.getPlaces(offset, coordinates)
                    mainActivityViewModel.hasStartLoader.postValue(true)
                }
            }
            listState.OnBottomReached(buffer = 4) {
                offset += getProperty("places_offset").toInt()
                mainActivityViewModel.getPlaces(offset, coordinates)
                mainActivityViewModel.hasStartLoader.postValue(true)
            }
        }
    }

    private fun requestLocation() {
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getLocation()
            }

            else -> requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun getLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                coordinates = Pair(it.latitude, it.longitude)
                mainActivityViewModel.getPlaces(offset, coordinates)
            } else {
                locationMalfunction()
            }
        }.addOnFailureListener {
            locationMalfunction()
        }
    }

    private fun locationMalfunction() {
        Toast.makeText(this, "Can't load your location. Check your settings", Toast.LENGTH_LONG)
            .show()
        mainActivityViewModel.getPlaces(offset, coordinates)
    }
}