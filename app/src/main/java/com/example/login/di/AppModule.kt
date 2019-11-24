package com.example.login.di

import android.app.Application
import com.example.loginrx.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
 class AppModule {

    @Provides
    fun getApp(application: Application?): Boolean {
        return application == null
    }

    @Singleton
    @Provides
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideGoogleSignInClient(application: Application) : GoogleSignInOptions {
       return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.applicationContext.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

}