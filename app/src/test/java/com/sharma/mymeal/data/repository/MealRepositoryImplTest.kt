package com.sharma.mymeal.data.repository

import com.sharma.mymeal.data.model.MealDTO
import com.sharma.mymeal.data.model.MealsDTO
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.MealsRepository
import junit.framework.TestCase
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
import retrofit2.Response

class MealRepositoryImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    private val testDTO = MealsDTO(
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
                "Gulab Jamun",
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

    @Mock
    lateinit var fakeAPIHelper: ApiHelper
    private var repo: MealsRepository? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        repo = MealRepositoryImpl(fakeAPIHelper)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called, it returns the expected value`() = runTest {

        Mockito.`when`(fakeAPIHelper.getMeals("Dessert")).thenReturn(Response.success(testDTO))
        val sut = repo?.getMeals("Dessert")
        assertFalse(sut?.body()?.meals.isNullOrEmpty())
        assertTrue(sut?.body()?.meals?.first()?.idMeal == "1")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called, it returns the null`() = runTest {

        Mockito.`when`(fakeAPIHelper.getMeals("Dessert")).thenReturn(null)
        val sut = repo?.getMeals("Dessert")
        assertTrue(sut == null)
    }


    @Test
    fun `when convertToMeals is called attributes of MealDTO and Meal should be same`() {
        val sut = repo?.convertToMeals(testDTO)
        TestCase.assertEquals(testDTO.meals?.firstOrNull()?.idMeal.orEmpty(), sut?.first()?.id)
        TestCase.assertEquals(
            testDTO.meals?.firstOrNull()?.strMeal.orEmpty(),
            sut?.first()?.name
        )
        TestCase.assertEquals(
            testDTO.meals?.firstOrNull()?.strMealThumb.orEmpty(),
            sut?.first()?.image
        )
    }

    @Test
    fun `when convertToMeals is called size of both lists should be same`() {
        val sut = repo?.convertToMeals(testDTO)
        val categoryDTOListSize = testDTO.meals?.size
        val convertedListSize = sut?.size ?: 0
        assert(sut != null)
        assertTrue(convertedListSize == categoryDTOListSize)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}