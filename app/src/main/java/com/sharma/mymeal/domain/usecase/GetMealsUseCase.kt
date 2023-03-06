package com.sharma.mymeal.domain.usecase

import com.sharma.mymeal.data.model.toDomainMeal
import com.sharma.mymeal.domain.model.Meal
import com.sharma.mymeal.domain.repository.MealsRepository
import com.sharma.mymeal.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetMealUseCase constructor(private val repository: MealsRepository) {

    operator fun invoke(category: String): Flow<Resource<List<Meal>>> = flow {
        try {
            emit(Resource.loading(arrayListOf()))
            val data = repository.getMeals(category)
            val domainData =
                if (!data?.meals.isNullOrEmpty()) data?.meals?.map { it -> it.toDomainMeal() } else emptyList()
            emit(Resource.success(data = domainData))
        } catch (e: HttpException) {
            emit(Resource.error(msg = e.localizedMessage ?: "An Unknown error occurred", null))
        } catch (e: IOException) {
            emit(Resource.error(msg = e.localizedMessage ?: "Check Connectivity", null))
        } catch (_: Exception) {
        }
    }


}