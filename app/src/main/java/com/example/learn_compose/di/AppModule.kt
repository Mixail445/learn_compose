package com.example.learn_compose.di

import com.example.learn_compose.data.Api
import com.example.learn_compose.data.RemoteSourceImpl
import com.example.learn_compose.data.RepositoryImpl
import com.example.learn_compose.domain.RemoteSource
import com.example.learn_compose.domain.Repository
import com.example.learn_compose.ui.screens.screenServer.BASE_URL
import com.example.learn_compose.utils.DispatchersProvider
import com.example.learn_compose.utils.DispatchersProviderImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Singleton
    @Provides
    fun provideDispatcher(): DispatchersProvider = DispatchersProviderImpl

    @Singleton
    @Provides
    fun provideRemoteSource(
        api: Api,
        dispatchersProvider: DispatchersProvider,
    ): RemoteSource = RemoteSourceImpl(api, dispatchersProvider)

    @Singleton
    @Provides
    fun provideRepository(repositoryImpl: RepositoryImpl): Repository = repositoryImpl

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor())
            .build()

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi
            .Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
}
