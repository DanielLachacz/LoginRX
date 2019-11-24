package com.example.login.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.login.ui.account.AccountActivity
import com.example.login.ui.login.LoginActivity
import com.example.loginrx.R
import com.example.loginrx.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : DaggerAppCompatActivity(), RegisterListener {

    lateinit var registerViewModel: RegisterViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_register
        )

        registerViewModel = ViewModelProviders.of(this, factory).get(RegisterViewModel::class.java)
        binding.registerViewModel = registerViewModel

        auth = FirebaseAuth.getInstance()

        registerViewModel.registerListener = this

        register_button.setOnClickListener {
            signUp()
        }

        go_login_button.setOnClickListener {
            openLoginActivity()
        }
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun signUp() {
        if ( password_edit_text.text.toString().trim() != password_confirm_edit_text.text.toString().trim()
            && email_edit_text.text.toString().trim().isNotEmpty()) {
            Toast.makeText(this, "Passwords are different!", Toast.LENGTH_SHORT).show()

        }
        else if(password_edit_text.text.toString().trim().isBlank() && password_confirm_edit_text.text.toString().trim().isBlank()
            && email_edit_text.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Fields are empty!", Toast.LENGTH_SHORT).show()
        }

        else if (password_edit_text.text.toString().trim() == password_confirm_edit_text.text.toString().trim()
            && email_edit_text.toString().trim().isNotEmpty()) {
            registerViewModel.signUp()

            password_edit_text.setText("")
            password_confirm_edit_text.setText("")
            email_edit_text.setText("")
        }

    }

    private fun openAccountActivity() {
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
    }

    override fun onStarted() {
        progress_bar.visibility = View.VISIBLE
        Log.e("Register", "onStarted")
    }

    override fun onSuccess() {
        progress_bar.visibility = View.GONE
        openAccountActivity()
        Log.e("Register", "onSuccess")
    }

    override fun onFailure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        Log.e("Register", "onFailure")
    }


}