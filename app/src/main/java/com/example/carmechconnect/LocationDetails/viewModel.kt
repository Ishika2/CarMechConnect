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
    private val _poiList = MutableLiveData<List<pointOfIntrestItem>?>()
    val poiList: MutableLiveData<List<pointOfIntrestItem>?> = _poiList

    init {
        fetchMac()
    }

    fun fetchMac() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = MechanicService.mechanicInstance.getMechanic()
                if(response.isSuccessful){
                    val mach = response.body()?.item
                    _poiList.value = mach
                }else{
                    Log.i("machanic response", "Failed to Load the News")
                }
            }catch (e:Exception){
                Log.i("exception", e.message.toString())
            }
        }
    }
}