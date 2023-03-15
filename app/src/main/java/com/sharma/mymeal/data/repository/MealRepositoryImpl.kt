package com.sharma.mymeal.data.repository

import com.sharma.mymeal.data.mapper.MealMapper
import com.sharma.mymeal.domain.model.Meal
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.MealsRepository
import com.sharma.mymeal.utils.Constants
import com.sharma.mymeal.utils.Result
import retrofit2.HttpException
import java.io.IOException

class MealRepositoryImpl(private val apiHelper: ApiHelper, private val mapper: MealMapper) :
    MealsRepository {
    override suspend fun getMeals(category: String): Result<List<Meal>> {
        return try {
            val result = apiHelper.getMeals(category)
            if (result.isSuccessful) {
                Result.Success(mapper.convertToMeals(result.body()))
            } else {
                Result.Error(result.errorBody()?.string().orEmpty())
            }
        } catch (e: HttpException) {
            Result.Error(e.localizedMessage ?: Constants.UNKNOWN_ERROR)
        } catch (e: IOException) {
            Result.Error(e.localizedMessage ?: Constants.INTERNET_CONNECTIVITY_ERROR)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: Constants.UNKNOWN_ERROR)
        }
    }
}