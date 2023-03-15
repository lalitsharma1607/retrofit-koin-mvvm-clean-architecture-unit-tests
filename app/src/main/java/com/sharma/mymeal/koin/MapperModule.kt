package com.sharma.mymeal.koin

import com.sharma.mymeal.data.mapper.CategoryMapper
import com.sharma.mymeal.data.mapper.MealMapper
import org.koin.dsl.module

val mapperModule = module {
    factory {
        return@factory MealMapper()
    }

    factory {
        return@factory CategoryMapper()
    }
}