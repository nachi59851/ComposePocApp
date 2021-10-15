package com.example.composepocapp.di

import android.content.Context
import com.example.composepocapp.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//ApplicationComponent is now renamed to SingletonComponent
@Module
@InstallIn(SingletonComponent :: class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) : BaseApplication{
        return app as BaseApplication
    }
}