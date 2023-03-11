package com.sharma.mymeal.domain.usecase

import com.sharma.mymeal.domain.model.Category
import com.sharma.mymeal.domain.repository.CategoriesRepository
import com.sharma.mymeal.utils.Constants
import com.sharma.mymeal.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCategoriesUseCase constructor(private val repository: CategoriesRepository) {

    operator fun invoke(): Flow<Resource<List<Category>>> = flow {
        try {
            emit(Resource.loading(arrayListOf()))
            val response = repository.getCategories()
            val data = response.body()
            if (response.isSuccessful) {
                val domainData = data?.let { repository.convertToCategories(it) }
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