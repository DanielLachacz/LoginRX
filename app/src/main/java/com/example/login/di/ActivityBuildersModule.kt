package com.example.login.di

import com.example.login.ui.account.AccountActivity
import com.example.login.ui.login.LoginActivity
import com.example.login.ui.login.LoginViewModelsModule
import com.example.login.ui.register.RegisterActivity
import com.example.login.ui.register.RegisterViewModelsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [LoginViewModelsModule::class])
    abstract fun contributeLoginActivity(): LoginActivity?

    @ContributesAndroidInjector(modules = [RegisterViewModelsModule::class])
    abstract fun contributeRegisterActivity(): RegisterActivity?

    @ContributesAndroidInjector
    abstract fun contributeAccountActivity(): AccountActivity?
}