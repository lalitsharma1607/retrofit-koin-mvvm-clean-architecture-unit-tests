package com.sharma.mymeal.data.repository

import com.sharma.mymeal.data.mapper.CategoryMapper
import com.sharma.mymeal.domain.model.Category
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.CategoriesRepository
import com.sharma.mymeal.utils.Constants
import com.sharma.mymeal.utils.Result
import okio.IOException
import retrofit2.HttpException

class CategoryRepositoryImpl(private val apiHelper: ApiHelper, private val mapper: CategoryMapper) :
    CategoriesRepository {
    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            val result = apiHelper.getCategories()
            if (result.isSuccessful) {
                Result.Success(result.body()?.let { mapper.convertToCategories(it) }.orEmpty())
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