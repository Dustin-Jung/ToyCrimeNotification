package com.android.aop.part2.toycrimenotification.data.repo

import com.android.aop.part2.toycrimenotification.data.source.remote.FirebaseRemoteDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(private val firebaseRemoteDataSource: FirebaseRemoteDataSource) :
    FirebaseRepository {

    override suspend fun login(id: String, password: String): Task<AuthResult> =
        firebaseRemoteDataSource.login(id, password)

    override suspend fun logout(): Boolean =
        firebaseRemoteDataSource.logout()

    override suspend fun register(id: String, password: String): Task<AuthResult> =
        firebaseRemoteDataSource.register(id, password)

    override suspend fun delete(): Task<Void>? =
        firebaseRemoteDataSource.delete()

    override suspend fun resetPass(resetPassToId: String): Task<Void> =
        firebaseRemoteDataSource.resetPass(resetPassToId)

    override fun getFirebaseAuth(): FirebaseAuth =
        firebaseRemoteDataSource.getFirebaseAuth()
}