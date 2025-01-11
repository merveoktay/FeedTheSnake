package com.example.feedthesnake.di

import android.app.Application
import androidx.room.Room
import com.example.feedthesnake.configs.AppDatabase
import com.example.feedthesnake.dao.SnakeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "snakes")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSnakeDao(database: AppDatabase): SnakeDao {
        return database.snakeDao()
    }
}