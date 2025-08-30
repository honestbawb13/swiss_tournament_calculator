package com.example.swiss.di

import android.content.Context
import androidx.room.Room
import com.example.swiss.data.SwissDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext ctx: Context): SwissDatabase =
        Room.databaseBuilder(ctx, SwissDatabase::class.java, "swiss.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideRepository(db: SwissDatabase) = com.example.swiss.data.TournamentRepository(db)
}
