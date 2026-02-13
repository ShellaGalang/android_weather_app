package com.example.weatherapp.data.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

// Implements LocationProvider(Interface)
class FusedLocationProviderImpl(
    private val context: Context
) : LocationProvider {

    // Google Play Services location client. Knows how to talk to GPS, WI-Fi, cell towers
    private val client = LocationServices.getFusedLocationProviderClient(context)

    // This will be what ViewModel will call
    override suspend fun getCurrentLocation(): LocationResult  =
        suspendCancellableCoroutine { cont ->
            // Request last known location(battery-friendy)
            @SuppressLint("MissingPermission")
            client.lastLocation
                // Callback fired when location request succeeds
                .addOnSuccessListener { location ->
                    // Success does not guarantee non-null location - hence this checking
                    if (location != null) {
                        // Resume suspended coroutine, return Success(lat, lon) to caller
                        cont.resume(
                            // Set Sealed Class Success, to be used by the ViewModel
                            LocationResult.Success(
                                lat = location.latitude,
                                lon = location.longitude
                            )
                        )
                    } else {
                        // Explicit failure. UI can show Location Unavailable
                        cont.resume(LocationResult.LocationUnavailable)
                    }
                }
                // Any exception during location fetch
                .addOnFailureListener {
                    cont.resume(LocationResult.LocationUnavailable)
                }
        }
}