package com.sharma.mymeal.presentation.meal_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.sharma.mymeal.data.model.MealDTO
import com.sharma.mymeal.data.model.MealsDTO
import com.sharma.mymeal.domain.repository.MealsRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MealsViewModelTest {

    private val fakeCategory: String = "Dessert"

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()
    private val viewModel: MealsViewModel? = null

    @Mock
    val fakeCategoryRepository: MealsRepository =
        Mockito.mock(MealsRepository::class.java)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called, isLoading is true`() = runTest {
        viewModel?.getMeals(fakeCategory)
        viewModel?.meals?.test {
            val emission = awaitItem()
            assertTrue(emission.isLoading)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called, isLoading is false and data is not empty`() = runTest {
        viewModel?.getMeals(fakeCategory)
        val meals = MealsDTO(
            arrayListOf(
                MealDTO(
                    null,
                    "1",
                    null,
                    fakeCategory,
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
                    null

                )
            )
        )
        Mockito.`when`(fakeCategoryRepository.getMeals(fakeCategory)).thenReturn(meals)
        viewModel?.getMeals(fakeCategory)
        viewModel?.meals?.test {
            val emission2 = awaitItem()
            assertTrue(emission2.data?.size == 1)
            assertTrue(emission2.data?.first()?.id == "1")
            assertTrue(emission2.data?.first()?.name == fakeCategory)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}