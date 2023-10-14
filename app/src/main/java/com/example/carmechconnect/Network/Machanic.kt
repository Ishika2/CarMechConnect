package com.example.carmechconnect.Network

import com.example.carmechconnect.LocationDetails.POI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL_mechanic = "https://nominatim.openstreetmap.org/"
val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

// Define the complete URL in the annotation
interface MechanicInterface {
    @GET("search?addressdetails=1&q=Car+mechanic&format=jsonv2&limit=10")
    suspend fun getMechanic(): Response<POI>
}

object MechanicService {
    val mechanicInstance: MechanicInterface

    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL_mechanic)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        mechanicInstance = retrofit.create(MechanicInterface::class.java)
    }
}





