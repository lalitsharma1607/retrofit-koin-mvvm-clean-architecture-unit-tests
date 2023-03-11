package com.sharma.mymeal.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.sharma.mymeal.data.model.MealDTO
import com.sharma.mymeal.data.model.MealsDTO
import com.sharma.mymeal.data.model.toDomainMeal
import com.sharma.mymeal.domain.repository.MealsRepository
import com.sharma.mymeal.utils.Status
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class GetMealsUseCaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()
    private val fakeName = "Gulab Jamun"
    private val fakeCategory: String = "Dessert"

    @Mock
    val fakeRepo: MealsRepository = mock(MealsRepository::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetCategories_emptyList() = runTest {
        `when`(fakeRepo.getMeals(fakeCategory)).thenReturn(Response.success(MealsDTO(arrayListOf())))

        val getMealUseCase =
            GetMealUseCase(fakeRepo).invoke(fakeCategory).toList()[1].data
        assertTrue(getMealUseCase?.isEmpty() == true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetCategories_requestedList() = runTest {
        val meals = MealsDTO(
            arrayListOf(
                MealDTO(
                    null,
                    "1",
                    null,
                    "Dessert",
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
                    fakeName,
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
        `when`(fakeRepo.getMeals(fakeCategory)).thenReturn(Response.success(meals))
        `when`(fakeRepo.convertToMeals(meals)).thenReturn(meals.meals?.map { it.toDomainMeal() })

        GetMealUseCase(fakeRepo).invoke(fakeCategory).test {
            val awaited = awaitItem()
            assertTrue(awaited.status == Status.LOADING)
            awaited.data?.isEmpty()?.let { assertTrue(it) }

            val awaited2 = awaitItem()
            assertTrue(awaited2.status == Status.SUCCESS)
            assertTrue(awaited2.data?.size == 1)
            assertTrue(awaited2.data?.firstOrNull()?.name == fakeName)

            cancelAndIgnoreRemainingEvents()
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}