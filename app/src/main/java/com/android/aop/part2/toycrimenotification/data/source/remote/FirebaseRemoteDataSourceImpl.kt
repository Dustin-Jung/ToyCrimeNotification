package com.android.aop.part2.toycrimenotification.data.source.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseRemoteDataSourceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    FirebaseRemoteDataSource {

    override suspend fun login(id: String, password: String): Task<AuthResult> =

        firebaseAuth.signInWithEmailAndPassword(id,password)

    override suspend fun logout(): Boolean =
        try {
            firebaseAuth.signOut()
            firebaseAuth.currentUser == null
        } catch (e: Exception) {
            false
        }

    override suspend fun register(id: String, password: String): Task<AuthResult> =
        firebaseAuth.createUserWithEmailAndPassword(id,password)

    override suspend fun delete(): Task<Void>? =
        firebaseAuth.currentUser?.delete()

    override suspend fun resetPass(resetPassToId: String): Task<Void> =
        firebaseAuth.sendPasswordResetEmail(resetPassToId)

    override fun getFirebaseAuth(): FirebaseAuth = firebaseAuth
}