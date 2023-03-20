package com.sharma.mymeal.presentation.meal_list

import app.cash.turbine.test
import com.sharma.mymeal.common.Constants
import com.sharma.mymeal.domain.model.Meal
import com.sharma.mymeal.domain.repository.MealsRepository
import com.sharma.mymeal.domain.usecase.GetMealUseCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MealsViewModelTest {

    private val fakeMealList = arrayListOf(
        Meal(
            Constants.FAKE_ID,
            Constants.FAKE_NAME,
            Constants.FAKE_THUMB
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()
    private var viewModel: MealsViewModel? = null

    @Mock
    var fakeUseCase: GetMealUseCase = Mockito.mock(GetMealUseCase::class.java)

    @Mock
    val repository: MealsRepository = Mockito.mock(MealsRepository::class.java)


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        fakeUseCase = GetMealUseCase(repository)
        viewModel = MealsViewModel(fakeUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getMeals is called then state is Loading`() = runTest {
        viewModel?.getMeals(Constants.testCategory)
        viewModel?.meals?.test {
            val emission = awaitItem()
            assertTrue(emission is MealListState.Loading)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getMeals is called then state is Success and data is not empty`() = runTest {

        Mockito.`when`(fakeUseCase.invoke(Constants.testCategory)).thenReturn(
            com.sharma.mymeal.utils.Result.Success(fakeMealList)
        )
        viewModel?.getMeals(Constants.testCategory)
        advanceUntilIdle()
        viewModel?.meals?.test {
            val emission = awaitItem()
            assert(emission is MealListState.Data)
            assertTrue((emission as MealListState.Data).data.size == 1)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getMeals is called then state is success and all attributes are as expected`() =
        runTest {
            Mockito.`when`(fakeUseCase.invoke(Constants.testCategory)).thenReturn(
                com.sharma.mymeal.utils.Result.Success(fakeMealList)
            )
            viewModel?.getMeals(Constants.testCategory)
            advanceUntilIdle()
            viewModel?.meals?.test {
                val emission = awaitItem()
                assert(emission is MealListState.Data)
                assertTrue((emission as MealListState.Data).data.size == 1)
                assertTrue(emission.data.firstOrNull()?.name == Constants.FAKE_NAME)
                assertTrue(emission.data.firstOrNull()?.image == Constants.FAKE_THUMB)
            }
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}