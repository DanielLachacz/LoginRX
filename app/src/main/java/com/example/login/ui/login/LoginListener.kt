package com.example.login.ui.login

interface LoginListener {

    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}