package com.sharma.mymeal.domain.usecase

import com.sharma.mymeal.data.model.toDomainCategory
import com.sharma.mymeal.domain.model.Category
import com.sharma.mymeal.domain.repository.CategoriesRepository
import com.sharma.mymeal.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCategoriesUseCase constructor(private val repository: CategoriesRepository) {

    operator fun invoke(): Flow<Resource<List<Category>>> = flow {
        try {
            emit(Resource.loading(arrayListOf()))
            val data = repository.getCategories()
            val domainData =
                if (data?.categories?.isNotEmpty() == true) data.categories.map { it -> it.toDomainCategory() } else emptyList()
            emit(Resource.success(data = domainData))
        } catch (e: HttpException) {
            emit(Resource.error(msg = e.localizedMessage ?: "An Unknown error occurred", null))
        } catch (e: IOException) {
            emit(Resource.error(msg = e.localizedMessage ?: "Check Connectivity", null))
        } catch (_: Exception) {
        }
    }

}