package com.sharma.mymeal.data.repository

import com.sharma.mymeal.common.Constants.testCategory
import com.sharma.mymeal.data.mapper.MealMapper
import com.sharma.mymeal.data.model.MealDTO
import com.sharma.mymeal.data.model.MealsDTO
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.MealsRepository
import com.sharma.mymeal.utils.Result
import com.sharma.mymeal.utils.Constants
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doAnswer
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MealRepositoryImplTest {

    companion object {
        const val FAKE_NAME = "Gulab Jamun"
    }

    @Mock
    lateinit var fakeAPIHelper: ApiHelper

    private var repo: MealsRepository? = null

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repo = MealRepositoryImpl(fakeAPIHelper, MealMapper())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getMeals is called then it returns the expected value`() = runTest {

        Mockito.`when`(fakeAPIHelper.getMeals(testCategory))
            .thenReturn(Response.success(getTestDTO()))
        val sut = repo?.getMeals(testCategory)
        assert(sut is Result.Success)
        val result = sut as Result.Success?
        assertFalse(result?.data.isNullOrEmpty())
        assertTrue(result?.data?.first()?.name == FAKE_NAME)
        assertTrue(result?.data?.first()?.image.isNullOrEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getMeals is called then it returns the null`() = runTest {

        Mockito.`when`(fakeAPIHelper.getMeals(testCategory)).thenReturn(null)
        val sut = repo?.getMeals(testCategory)
        assertTrue(sut is Result.Error)
        val result = sut as Result.Error?
        assertTrue(result?.error == Constants.UNKNOWN_ERROR)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then it gives error when Http exception occurs`() =
        runTest {
            Mockito.`when`(fakeAPIHelper.getMeals(testCategory)).thenThrow(HttpException::class.java)
            val sut = repo?.getMeals(testCategory)
            assertTrue(sut is Result.Error)
            val result = sut as Result.Error?
            assertTrue(result?.error == Constants.UNKNOWN_ERROR)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then it gives error when IO exception occurs`() =
        runTest {
            Mockito.`when`(fakeAPIHelper.getMeals(testCategory)).doAnswer { throw IOException(Constants.INTERNET_CONNECTIVITY_ERROR) }
            val sut = repo?.getMeals(testCategory)
            assertTrue(sut is Result.Error)
            val result = sut as Result.Error?
            assertTrue(result?.error == Constants.INTERNET_CONNECTIVITY_ERROR)
        }

    private fun getTestDTO(id: String? = "1", category: String? = "Dessert", mealName: String? = "Gulab Jamun"): MealsDTO {
        return MealsDTO(
            arrayListOf(
                MealDTO(
                    null,
                    id,
                    null,
                    category,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    mealName,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null

                )
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}