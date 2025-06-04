package com.example.posts.di

import android.content.Context
import androidx.room.Room
import com.example.posts.data.db.AppDatabase
import com.example.posts.data.db.PostDao
import com.example.posts.data.network.PostApi
import com.example.posts.data.repository.PostRepository
import com.example.posts.data.repository.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providePostApi(retrofit: Retrofit): PostApi =
        retrofit.create(PostApi::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "posts_database"
        ).build()

    @Provides
    @Singleton
    fun providePostDao(database: AppDatabase): PostDao = database.postDao()

    @Provides
    @Singleton
    fun providePostRepository(api: PostApi, dao: PostDao): PostRepository =
        PostRepositoryImpl(api, dao)
}