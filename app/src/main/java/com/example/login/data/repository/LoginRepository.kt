package com.example.login.data.repository

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import javax.inject.Inject

class LoginRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {


    fun signIn(email: String, password: String) = Completable.create{ emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
                    emitter.onComplete()
                    Log.e("LoginRepository", "Ok")
                } else {
                    emitter.onError(it.exception!!)
                    Log.e("LoginRepository", "Error")
                }
            }
        }
    }

    fun signInWithGoogle(authCredential: AuthCredential) = Completable.create { emitter ->
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
                    emitter.onComplete()
                    Log.e("LoginRepository", "Ok")
                } else
                    emitter.onError(it.exception!!)
                Log.e("LoginRepository", "Error")
            }
        }
    }

        //version without RXjava
    fun signIn2(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e("LoginRepository", "signInWithEmail:success")
                    val user = firebaseAuth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("LoginRepository", "signInWithEmail:failure", task.exception)
                }
            }
    }


}