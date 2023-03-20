package com.sharma.mymeal.presentation.meal_categories

import app.cash.turbine.test
import com.sharma.mymeal.common.Constants.FAKE_NAME
import com.sharma.mymeal.common.Constants.FAKE_THUMB
import com.sharma.mymeal.domain.model.Category
import com.sharma.mymeal.domain.repository.CategoriesRepository
import com.sharma.mymeal.domain.usecase.GetCategoriesUseCase
import com.sharma.mymeal.utils.Constants
import com.sharma.mymeal.utils.Result
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CategoriesViewModelTest {

    private val fakeCategory = arrayListOf(Category(FAKE_NAME, FAKE_THUMB))

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()
    private var viewModel: CategoriesViewModel? = null

    @Mock
    var fakeUseCase: GetCategoriesUseCase =
        Mockito.mock(GetCategoriesUseCase::class.java)

    @Mock
    val repository: CategoriesRepository = Mockito.mock(CategoriesRepository::class.java)


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        fakeUseCase = GetCategoriesUseCase(repository)
        viewModel = CategoriesViewModel(fakeUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then state is Loading`() = runTest {
        viewModel?.getCategories()
        viewModel?.categories?.test {
            val emission = awaitItem()
            assertTrue(emission is CategoryListState.Loading)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then state is Success and data is not empty`() = runTest {

        Mockito.`when`(fakeUseCase.invoke()).thenReturn(
            Result.Success(fakeCategory)
        )
        viewModel?.getCategories()
        advanceUntilIdle()
        viewModel?.categories?.test {
            val emission = awaitItem()
            assert(emission is CategoryListState.Data)
            assertTrue((emission as CategoryListState.Data).data.size == 1)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then state is success and all attributes are as expected`() =
        runTest {
            Mockito.`when`(fakeUseCase.invoke()).thenReturn(
                Result.Success(fakeCategory)
            )
            viewModel?.getCategories()
            advanceUntilIdle()
            viewModel?.categories?.test {
                val emission = awaitItem()
                assert(emission is CategoryListState.Data)
                assertTrue((emission as CategoryListState.Data).data.size == 1)
                assertTrue(emission.data.firstOrNull()?.name == FAKE_NAME)
                assertTrue(emission.data.firstOrNull()?.thumb == FAKE_THUMB)
            }
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then state is error when UNKNOWN_ERROR occurs`() =
        runTest {
            Mockito.`when`(fakeUseCase.invoke()).thenReturn(Result.Error(Constants.UNKNOWN_ERROR))
            testDispatcher.invoke {
                viewModel?.getCategories()
            }
            viewModel?.categories?.test {
                val emission = awaitItem()
                assert(emission is CategoryListState.Error)
                assertTrue((emission as CategoryListState.Error).error.toString() == Constants.UNKNOWN_ERROR)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then state is error when INTERNET_CONNECTIVITY_ERROR occurs`() =
        runTest {
            Mockito.`when`(fakeUseCase.invoke()).thenReturn(Result.Error(Constants.INTERNET_CONNECTIVITY_ERROR))
            testDispatcher.invoke {
                viewModel?.getCategories()
            }
            viewModel?.categories?.test {
                val emission = awaitItem()
                assert(emission is CategoryListState.Error)
                assertTrue((emission as CategoryListState.Error).error.toString() == Constants.INTERNET_CONNECTIVITY_ERROR)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}