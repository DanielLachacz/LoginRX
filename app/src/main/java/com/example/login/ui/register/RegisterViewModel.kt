package com.example.login.ui.register

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.login.data.repository.RegisterRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegisterViewModel @Inject constructor( private val registerRepository: RegisterRepository
): ViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    var registerListener: RegisterListener? = null
    private val disposables = CompositeDisposable()

    fun signUp() {

        registerListener?.onStarted()

        val disposable = registerRepository.signUp(email.get().toString(), password.get().toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                registerListener?.onSuccess()
            }, {
                registerListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}