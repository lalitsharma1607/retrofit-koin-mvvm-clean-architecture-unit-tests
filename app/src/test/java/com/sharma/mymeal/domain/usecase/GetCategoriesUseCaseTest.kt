package com.sharma.mymeal.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.CategoryDTO
import com.sharma.mymeal.data.model.toDomainCategory
import com.sharma.mymeal.domain.repository.CategoriesRepository
import com.sharma.mymeal.utils.Status
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class GetCategoriesUseCaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val fakeId = "1"
    private val fakeName = "Chicken"

    @Mock
    val fakeCategoryRepository: CategoriesRepository = mock(CategoriesRepository::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetCategories_emptyList() = runTest {
        `when`(fakeCategoryRepository.getCategories()).thenReturn(
            Response.success(
                CategoriesDTO(
                    arrayListOf()
                )
            )
        )

        val getCategoriesUseCase =
            GetCategoriesUseCase(fakeCategoryRepository).invoke().toList()[1].data
        assertTrue(getCategoriesUseCase?.isEmpty() == true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetCategories_requestedList() = runTest {
        val testDTO = CategoriesDTO(
            arrayListOf(
                CategoryDTO(
                    idCategory = fakeId,
                    strCategory = fakeName,
                    strCategoryDescription = null,
                    strCategoryThumb = null
                )
            )
        )
        `when`(fakeCategoryRepository.getCategories()).thenReturn(
            Response.success(
                testDTO
            )
        )
        `when`(fakeCategoryRepository.convertToCategories(testDTO)).thenReturn(testDTO.categories.map { it.toDomainCategory() })
        GetCategoriesUseCase(fakeCategoryRepository).invoke().test {
            val awaited = awaitItem()
            assertTrue(awaited.status == Status.LOADING)
            awaited.data?.isEmpty()?.let { assertTrue(it) }

            val awaited2 = awaitItem()
            assertTrue(awaited2.status == Status.SUCCESS)
            assertTrue(awaited2.data?.size == 1)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}