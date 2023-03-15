package com.sharma.mymeal.data.repository

import com.sharma.mymeal.common.Constants.testCategory
import com.sharma.mymeal.common.Constants.testMealDTO
import com.sharma.mymeal.data.mapper.MealMapper
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.MealsRepository
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
import retrofit2.Response

class MealRepositoryImplTest {

    @Mock
    lateinit var fakeAPIHelper: ApiHelper

    @Mock
    lateinit var fakeMapper: MealMapper

    private var repo: MealsRepository? = null

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repo = MealRepositoryImpl(fakeAPIHelper, fakeMapper)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getMeals is called, it returns the expected value`() = runTest {

        Mockito.`when`(fakeAPIHelper.getMeals(testCategory))
            .thenReturn(Response.success(testMealDTO))
        val sut = repo?.getMeals(testCategory)
        assert(sut is com.sharma.mymeal.utils.Result.Success)
        val result = sut as com.sharma.mymeal.utils.Result.Success?
        assertFalse(result?.data.isNullOrEmpty())
        assertTrue(result?.data?.first()?.name == "Gulab Jamun")
        assertTrue(result?.data?.first()?.image.isNullOrEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getMeals is called, it returns the null`() = runTest {

        Mockito.`when`(fakeAPIHelper.getMeals(testCategory)).thenReturn(null)
        val sut = repo?.getMeals(testCategory)
        assertTrue(sut is com.sharma.mymeal.utils.Result.Error)
        val result = sut as com.sharma.mymeal.utils.Result.Error?
        assertTrue(result?.error == com.sharma.mymeal.utils.Constants.UNKNOWN_ERROR)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}