package com.android.aop.part2.toycrimenotification.di

import com.android.aop.part2.toycrimenotification.data.repo.CriminalRepository
import com.android.aop.part2.toycrimenotification.data.repo.CriminalRepositoryImpl
import com.android.aop.part2.toycrimenotification.data.repo.KakaoRepository
import com.android.aop.part2.toycrimenotification.data.repo.KakaoRepositoryImpl
import com.android.aop.part2.toycrimenotification.data.source.local.CriminalLocalDataSource
import com.android.aop.part2.toycrimenotification.data.source.local.CriminalLocalDataSourceImpl
import com.android.aop.part2.toycrimenotification.data.source.remote.CriminalRemoteDataSource
import com.android.aop.part2.toycrimenotification.data.source.remote.CriminalRemoteDataSourceImpl
import com.android.aop.part2.toycrimenotification.data.source.remote.KakaoRemoteDataSource
import com.android.aop.part2.toycrimenotification.data.source.remote.KakaoRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCriminalRepository(criminalRepositoryImpl: CriminalRepositoryImpl): CriminalRepository

    @Singleton
    @Binds
    abstract fun bindCriminalRemoteDataSource(criminalRemoteDataSourceImpl: CriminalRemoteDataSourceImpl): CriminalRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindCriminalLocalDataSource(criminalLocalDataSourceImpl: CriminalLocalDataSourceImpl): CriminalLocalDataSource

    @Singleton
    @Binds
    abstract fun bindKakaoRepository(kakaoRepositoryImpl: KakaoRepositoryImpl): KakaoRepository

    @Singleton
    @Binds
    abstract fun bindKakaoRemoteDataSource(kakaoRemoteDataSourceImpl: KakaoRemoteDataSourceImpl): KakaoRemoteDataSource

}