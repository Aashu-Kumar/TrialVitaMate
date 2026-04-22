package com.aashu.trialvitamate.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("drug/label.json")
    fun searchMedicine(
        @Query("search") medicine: String
    ): Call<MedicineResponse>
}