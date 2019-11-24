package com.example.login.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.login.ui.account.AccountActivity
import com.example.login.ui.register.RegisterActivity
import com.example.loginrx.R
import com.example.loginrx.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity(), LoginListener {

    lateinit var loginViewModel: LoginViewModel
    private var googleSignInClient: GoogleSignInClient? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var auth: FirebaseAuth
    @Inject
    lateinit var googleSignInOptions: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_login
        )

        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel

        loginViewModel.loginListener = this

        auth = FirebaseAuth.getInstance()

        login_button.setOnClickListener {
            signIn()
        }

        google_login_button.setOnClickListener {
           initGoogleSignInClient()
            signInWithGoogle()
        }

        go_sing_up_button.setOnClickListener {
            openRegisterActivity()
        }

    }

    private fun signIn() {
        if (email_edit_text.text.toString().isNotEmpty() && password_edit_text.text.toString().isNotEmpty()) {
            loginViewModel.signIn()

            email_edit_text.setText("")
            password_edit_text.setText("")
        }
        else
            Toast.makeText(this, "Fields are empty!", Toast.LENGTH_SHORT).show()
    }

    private fun openRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val googleSignInAccount = task.getResult(
                    ApiException::class.java
                )
                if (googleSignInAccount != null) {
                    getGoogleAuthCredential(googleSignInAccount)
                }
            } catch (e: ApiException) {
                //logErrorMessage(e.message)
            }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, 123)
    }

    private fun initGoogleSignInClient() {
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }


    private fun getGoogleAuthCredential(googleSignInAccount: GoogleSignInAccount) {
        val googleTokenId = googleSignInAccount.idToken
        val googleAuthCredential =
            GoogleAuthProvider.getCredential(googleTokenId, null)
        signInWithGoogleAuthCredential(googleAuthCredential as GoogleAuthCredential)
    }

    private fun signInWithGoogleAuthCredential(authCredential: AuthCredential) {
        loginViewModel.signInWithGoogle(authCredential)
    }

    private fun openAccountActivity(currentUser: String) {
        val intent = Intent(this, AccountActivity::class.java)
        intent.putExtra("CurrentUser", currentUser)
        startActivity(intent)
    }

    override fun onStarted() {
        progress_bar.visibility = View.VISIBLE
        Log.e("Login", "onStarted")
    }

    override fun onSuccess() {
        progress_bar.visibility = View.GONE
        val currentUser = auth.currentUser?.email
        openAccountActivity(currentUser!!)
        Log.e("Login", "onSuccess")
    }

    override fun onFailure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        Log.e("Login", "onFailure")
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser?.email
        if (currentUser != null) {
            openAccountActivity(currentUser.toString())
        }


    }


}
