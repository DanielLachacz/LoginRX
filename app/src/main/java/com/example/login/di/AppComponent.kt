package com.example.login.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.login.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component (modules = [
    AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    ActivityBuildersModule::class,
    AppModule::class])

interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}