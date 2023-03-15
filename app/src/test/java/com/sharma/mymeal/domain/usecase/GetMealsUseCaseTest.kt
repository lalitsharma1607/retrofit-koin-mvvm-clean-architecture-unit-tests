package com.sharma.mymeal.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sharma.mymeal.common.Constants
import com.sharma.mymeal.domain.model.Meal
import com.sharma.mymeal.domain.repository.MealsRepository
import com.sharma.mymeal.utils.Result
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetMealsUseCaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    val repository: MealsRepository = mock(MealsRepository::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getMeals is called then it returns empty result`() = runTest {
        `when`(repository.getMeals(Constants.testCategory)).thenReturn(
            Result.Success(arrayListOf())
        )

        val result: Result<List<Meal>> =
            GetMealUseCase(repository).getMeals(Constants.testCategory)
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertTrue(data.isNullOrEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getMeals is called then it returns expected data`() = runTest {
        `when`(repository.getMeals(Constants.testCategory)).thenReturn(
            Result.Success(Constants.mealListSizeOne)
        )

        val result: Result<List<Meal>> =
            GetMealUseCase(repository).getMeals(Constants.testCategory)
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertTrue(data?.size == 1)
    }
}