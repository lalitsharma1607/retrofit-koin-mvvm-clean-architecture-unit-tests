package com.sharma.mymeal.domain.remote

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.MealsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoriesDTO?>

    @GET("search.php")
    suspend fun getMeal(@Query("s") category: String): Response<MealsDTO?>

}