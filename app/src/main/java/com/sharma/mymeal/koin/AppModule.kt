package com.sharma.mymeal.koin

import androidx.databinding.ktx.BuildConfig
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.remote.ApiService
import com.sharma.mymeal.domain.remote.impl.APIHelperImpl
import com.sharma.mymeal.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideOkHttpClient() }
    single {
        provideRetrofit(
            get(), Constants.BASE_URL
        )
    }
    single { provideApiService(get()) }

    single<ApiHelper> {
        return@single APIHelperImpl(get())
    }
}

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
} else OkHttpClient.Builder().build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient, BASE_URL: String
): Retrofit =
    Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
        .client(okHttpClient).build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)