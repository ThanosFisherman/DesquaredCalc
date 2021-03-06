package com.thanosfisherman.data_network.di

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.thanosfisherman.data_network.BuildConfig
import com.thanosfisherman.data_network.common.LoggingInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkingModule = module {

    single { LoggingInterceptor() }


    single {
        MoshiConverterFactory.create(
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        )
    }

    single {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val cache = Cache(androidContext().cacheDir, cacheSize)

        OkHttpClient.Builder().apply {
            addInterceptor(get<LoggingInterceptor>())
            cache(cache)
            connectTimeout(3000, TimeUnit.MILLISECONDS)
            callTimeout(12, TimeUnit.SECONDS)
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(get<MoshiConverterFactory>())
            .client(get())
            .build()
    }
}
