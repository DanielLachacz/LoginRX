package com.example.login.ui.register

import androidx.lifecycle.ViewModel
import com.example.login.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class RegisterViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(registerViewModel: RegisterViewModel?): ViewModel?
}