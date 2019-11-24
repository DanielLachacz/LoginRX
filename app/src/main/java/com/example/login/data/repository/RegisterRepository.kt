package com.example.login.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {


    fun signUp(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
                    emitter.onComplete()
                    Log.e("RegisterRepository", "Ok")
                } else {
                    emitter.onError(it.exception!!)
                    Log.e("RegisterRepository", "Error")
                }
            }
        }
    }

}