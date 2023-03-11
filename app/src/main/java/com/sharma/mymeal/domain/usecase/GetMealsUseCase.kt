package com.sharma.mymeal.domain.usecase

import com.sharma.mymeal.domain.model.Meal
import com.sharma.mymeal.domain.repository.MealsRepository
import com.sharma.mymeal.utils.Constants
import com.sharma.mymeal.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetMealUseCase constructor(private val repository: MealsRepository) {

    operator fun invoke(category: String): Flow<Resource<List<Meal>>> = flow {
        try {
            emit(Resource.loading(arrayListOf()))
            val response = repository.getMeals(category)
            val data = response.body()
            if (response.isSuccessful) {
                val domainData = data?.let { repository.convertToMeals(it) }
                emit(Resource.success(data = domainData))
            } else {
                emit(Resource.error(msg = response.errorBody()?.string().orEmpty(), null))
            }
        } catch (e: HttpException) {
            emit(Resource.error(msg = e.localizedMessage ?: Constants.UNKNOWN_ERROR, null))
        } catch (e: IOException) {
            emit(
                Resource.error(
                    msg = e.localizedMessage ?: Constants.INTERNET_CONNECTIVITY_ERROR, null
                )
            )
        } catch (_: Exception) {
        }
    }
}