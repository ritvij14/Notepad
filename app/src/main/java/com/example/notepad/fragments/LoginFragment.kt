package com.example.notepad.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.notepad.R
import com.example.notepad.activities.LoginActivity
import com.example.notepad.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    private lateinit var createAccBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var loginUsername: EditText
    private lateinit var loginPassword: EditText
    private lateinit var forgotPassword: TextView
    private var email: String? = null
    private var password: String? = null
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val loginFragmentLayout: View = inflater.inflate(R.layout.fragment_login, container, false)
        loginUsername = loginFragmentLayout.login_username
        loginPassword = loginFragmentLayout.login_password
        forgotPassword = loginFragmentLayout.forgot_password_button
        loginBtn= loginFragmentLayout.login_button
        createAccBtn= loginFragmentLayout.create_account_button

        firebaseAuth = FirebaseAuth.getInstance()

        forgotPassword.setOnClickListener {

        }

        loginBtn.setOnClickListener {

            if (loginUsername.text.isEmpty() && loginPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your credentials", Toast.LENGTH_SHORT).show()
                Log.d("LOGIN FRAGMENT", "Properly working")
                return@setOnClickListener
            }

            if (loginUsername.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (loginPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            email = loginUsername.text.toString()
            password = loginPassword.text.toString()

            loginTask(email!!, password!!)
        }

        createAccBtn.setOnClickListener {

            (context as LoginActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, SignupFragment.newInstance())
                .commit()
        }

        return loginFragmentLayout
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    private fun loginTask(email: String, pass: String) {

        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                var intent: Intent = Intent(activity, MainActivity::class.java)
                intent.putExtra("id", firebaseAuth.currentUser?.email)
                startActivity(intent)
            }

            else {
                Toast.makeText(activity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}