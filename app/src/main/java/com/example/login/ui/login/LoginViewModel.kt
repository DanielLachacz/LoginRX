package com.example.login.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.login.data.repository.LoginRepository
import com.google.firebase.auth.AuthCredential
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor( private val loginRepository: LoginRepository
): ViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    var loginListener: LoginListener? = null
    private val disposables = CompositeDisposable()


    fun signIn() {

        //authentication started
        loginListener?.onStarted()

        //calling login from repository to perform the actual authentication
        val disposable = loginRepository.signIn(email.get()!!, password.get()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                loginListener?.onSuccess()
            }, {
                //sending a failure callback
                loginListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun signInWithGoogle(authCredential: AuthCredential) {

        val disposable = loginRepository.signInWithGoogle(authCredential)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loginListener?.onSuccess()
            }, {
                loginListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
