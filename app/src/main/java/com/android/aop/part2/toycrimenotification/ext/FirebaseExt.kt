package com.android.aop.part2.toycrimenotification.ext

import com.android.aop.part2.toycrimenotification.data.repo.FirebaseRepository

fun FirebaseRepository.loginUser(
    email: String,
    password: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    getFirebaseAuth().signInWithEmailAndPassword(email, password)
        .addOnSuccessListener { callback(true) }
        .addOnCanceledListener { callback(false) }
}

fun FirebaseRepository.register(
    email: String,
    password: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    getFirebaseAuth().createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener { callback(true) }
        .addOnCanceledListener { callback(false) }
}