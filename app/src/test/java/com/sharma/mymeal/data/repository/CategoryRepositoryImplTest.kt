package com.sharma.mymeal.data.repository

import com.sharma.mymeal.data.mapper.CategoryMapper
import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.CategoryDTO
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.CategoriesRepository
import com.sharma.mymeal.utils.Result
import com.sharma.mymeal.utils.Constants
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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

class CategoryRepositoryImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var fakeAPIHelper: ApiHelper

    private var repo: CategoriesRepository? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        repo = CategoryRepositoryImpl(fakeAPIHelper, CategoryMapper())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then it returns the non empty result`() = runTest {
        Mockito.`when`(fakeAPIHelper.getCategories())
            .thenReturn(Response.success(getTestDTO()))
        val sut = repo?.getCategories()
        assert(sut is Result.Success)
        val result = sut as Result.Success?
        assertFalse(result?.data.isNullOrEmpty())
        assertTrue(result?.data?.first()?.name == "Chicken")
        assertTrue(result?.data?.first()?.thumb.isNullOrEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then it gives error when null is returned as a response`() =
        runTest {
            Mockito.`when`(fakeAPIHelper.getCategories()).thenReturn(null)
            val sut = repo?.getCategories()
            assertTrue(sut is Result.Error)
            val result = sut as Result.Error?
            assertTrue(result?.error == Constants.UNKNOWN_ERROR)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then it gives error when Http exception occurs`() =
        runTest {
            Mockito.`when`(fakeAPIHelper.getCategories()).thenThrow(HttpException::class.java)
            val sut = repo?.getCategories()
            assertTrue(sut is Result.Error)
            val result = sut as Result.Error?
            assertTrue(result?.error == Constants.UNKNOWN_ERROR)
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then it gives error when IO exception occurs`() =
        runTest {
            Mockito.`when`(fakeAPIHelper.getCategories()).doAnswer { throw IOException(Constants.INTERNET_CONNECTIVITY_ERROR) }
            val sut = repo?.getCategories()
            assertTrue(sut is Result.Error)
            val result = sut as Result.Error?
            assertTrue(result?.error == Constants.INTERNET_CONNECTIVITY_ERROR)
        }

    private fun getTestDTO(
        id: String? = "1",
        name: String? = "Chicken",
        desc: String? = null,
        thumb: String? = null
    ): CategoriesDTO {
        return CategoriesDTO(
            arrayListOf(
                CategoryDTO(
                    idCategory = id,
                    strCategory = name,
                    strCategoryDescription = desc,
                    strCategoryThumb = thumb
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