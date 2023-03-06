package com.sharma.mymeal.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.CategoryDTO
import com.sharma.mymeal.domain.repository.CategoriesRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
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

class GetCategoriesUseCaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()
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
        `when`(fakeCategoryRepository.getCategories()).thenReturn(CategoriesDTO(arrayListOf()))

        val getCategoriesUseCase =
            GetCategoriesUseCase(fakeCategoryRepository).invoke().toList()[1].data
        assertTrue(getCategoriesUseCase?.isEmpty() == true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetCategories_requestedList() = runTest {
        val list = CategoriesDTO(
            arrayListOf(
                CategoryDTO(
                    idCategory = fakeId,
                    strCategory = fakeName,
                    strCategoryDescription = null,
                    strCategoryThumb = null
                )
            )
        )
        `when`(fakeCategoryRepository.getCategories()).thenReturn(list)

        val getCategoriesUseCase =
            GetCategoriesUseCase(fakeCategoryRepository).invoke().toList()[1].data
        assertTrue(getCategoriesUseCase?.size == 1)
        assertTrue(getCategoriesUseCase?.getOrNull(0)?.name == fakeName)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}