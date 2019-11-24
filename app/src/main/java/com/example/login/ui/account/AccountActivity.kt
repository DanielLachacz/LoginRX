package com.example.login.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.login.ui.login.LoginActivity
import com.example.loginrx.R
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_account.*
import javax.inject.Inject

class AccountActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val currentUser = intent.getStringExtra("CurrentUser")
        test_text_view.text = currentUser

        log_out_button.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        auth.signOut()
        openLoginActivity()
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}