package com.example.login.ui.login

import androidx.lifecycle.ViewModel
import com.example.login.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class LoginViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel?): ViewModel?
}