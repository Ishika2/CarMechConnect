package com.example.carmechconnect.LocationDetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carmechconnect.Network.MechanicService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class viewModel: ViewModel() {
    private val _poiList = MutableLiveData<List<POI>>()
    val poiList: MutableLiveData<List<POI>> = _poiList

    init {
        fetchMac()
    }

    fun fetchMac() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = MechanicService.mechanicInstance.getMechanic()
                if (response.isSuccessful) {
                    val machanic = response.body()
                    Log.d("API Response", machanic.toString())

                    // Check if the response is not null
                    machanic?.let {
                        val lat = it.latitude
                        val long = it.longitude
                        val add = it.address
                        val name = it.name

                        // Create a POI object with the received data
                        val poi = POI(name = name, latitude = lat, longitude = long, address = add)
                        Log.d("POI", "Name: $name, Latitude: $lat, Longitude: $long, Address: $add")

                        // Update the POI list with the new POI
                        val currentList = _poiList.value.orEmpty().toMutableList()
                        currentList.add(poi)

                        // Update the MutableLiveData with the updated list
                        _poiList.value = currentList

                        // Log each POI's details
                        //Log.d("POI", "Name: $name, Latitude: $lat, Longitude: $long, Address: $add")
                    }
                } else {
                    Log.i("MachResponse", "Failed to load")
                }
            } catch (e: Exception) {
                Log.i("exception", e.message.toString())
            }
        }
    }
}