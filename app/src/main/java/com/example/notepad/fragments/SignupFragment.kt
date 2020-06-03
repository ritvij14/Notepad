package com.example.notepad.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notepad.R
import com.example.notepad.activities.LoginActivity
import com.example.notepad.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_signup.view.*

class SignupFragment : Fragment() {

    private lateinit var signupUsername: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signupConfirmPassword: EditText
    private lateinit var signupButton: Button
    private lateinit var loginFragmentBtn: Button
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var passConfirm: String
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val signupFragmentLayout: View = inflater.inflate(R.layout.fragment_signup, container, false)
        signupUsername = signupFragmentLayout.signup_username
        signupPassword = signupFragmentLayout.signup_password
        signupConfirmPassword = signupFragmentLayout.signup_confirm_password
        signupButton = signupFragmentLayout.signup_button
        loginFragmentBtn = signupFragmentLayout.login_button

        firebaseAuth = FirebaseAuth.getInstance()

        signupButton.setOnClickListener {

            if (signupUsername.text.isEmpty() && signupPassword.text.isEmpty() && signupConfirmPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (signupUsername.text.isEmpty() && signupPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (signupPassword.text.isEmpty() && signupConfirmPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (signupUsername.text.isEmpty() && signupConfirmPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (signupUsername.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (signupPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (signupConfirmPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please confirm your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            email = signupUsername.text.toString()
            password = signupPassword.text.toString()
            passConfirm = signupConfirmPassword.text.toString()

            signupTask(email, password, passConfirm)

        }

        loginFragmentBtn.setOnClickListener { v ->
            (context as LoginActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, LoginFragment.newInstance())
                .commit()
        }

        return signupFragmentLayout
    }

    companion object {
        fun newInstance(): SignupFragment {
            return SignupFragment()
        }
    }

    private fun signupTask(email: String, pass: String, passConf: String) {

        if(pass == passConf) {

            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    var intent: Intent = Intent(activity, MainActivity::class.java)
                    intent.putExtra("id", firebaseAuth.currentUser?.email)
                    startActivity(intent)
                }

                else {
                    Toast.makeText(activity, "Error!!!", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}