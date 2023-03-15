package com.sharma.mymeal.data.repository

import com.sharma.mymeal.common.Constants
import com.sharma.mymeal.data.mapper.CategoryMapper
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.CategoriesRepository
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class CategoryRepositoryImplTest {

    @Mock
    lateinit var fakeAPIHelper: ApiHelper

    @Mock
    lateinit var fakeMapper: CategoryMapper

    private var repo: CategoriesRepository? = null

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repo = CategoryRepositoryImpl(fakeAPIHelper, fakeMapper)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then it returns the non empty result`() = runTest {
        Mockito.`when`(fakeAPIHelper.getCategories())
            .thenReturn(Response.success(Constants.testCategoryDTO))
        val sut = repo?.getCategories()
        assert(sut is com.sharma.mymeal.utils.Result.Success)
        val result = sut as com.sharma.mymeal.utils.Result.Success?
        assertFalse(result?.data.isNullOrEmpty())
        assertTrue(result?.data?.first()?.name == "1")
        assertTrue(result?.data?.first()?.thumb.isNullOrEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then it gives error when null is returned as a response`() =
        runTest {

            Mockito.`when`(fakeAPIHelper.getCategories()).thenReturn(null)
            val sut = repo?.getCategories()
            assertTrue(sut is com.sharma.mymeal.utils.Result.Error)
            val result = sut as com.sharma.mymeal.utils.Result.Error?
            assertTrue(result?.error == com.sharma.mymeal.utils.Constants.UNKNOWN_ERROR)
        }
}