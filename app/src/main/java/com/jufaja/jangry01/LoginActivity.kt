package com.jufaja.jangry01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

private const val TAG = "Loginactivity"

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            goPostActivity()
        }
        btnLogin.setOnClickListener {
            btnLogin.isEnabled = false
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Email/Password NIET ingevuld", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // authantication check firebase

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { Task ->
                btnLogin.isEnabled = true
                if (Task.isSuccessful) {
                    Toast.makeText(this, "Login is OkiDoki",Toast.LENGTH_SHORT).show()
                    goPostActivity()
                }else {
                    Log.e(TAG, "Login is mislukt", Task.exception)
                    Toast.makeText(this, "Login is mislukt", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goPostActivity() {
        Log.i(TAG, "goPostActivity")
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
        finish()
    }
}