package com.example.login.ui.register

interface RegisterListener {

    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}