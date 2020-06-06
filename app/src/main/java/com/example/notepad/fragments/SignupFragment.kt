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
import com.example.notepad.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_signup.view.*

class SignupFragment : Fragment() {

    private lateinit var fragmentSignupBinding: FragmentSignupBinding

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var passConfirm: String
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentSignupBinding = FragmentSignupBinding.inflate(layoutInflater, container, false)
        var view = fragmentSignupBinding.root

        firebaseAuth = FirebaseAuth.getInstance()

        fragmentSignupBinding.signupButton.setOnClickListener {

            if (fragmentSignupBinding.signupUsername.text.isEmpty() &&
                fragmentSignupBinding.signupPassword.text.isEmpty() &&
                fragmentSignupBinding.signupConfirmPassword.text.isEmpty()) {

                Toast.makeText(activity, "Please enter your credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fragmentSignupBinding.signupUsername.text.isEmpty() &&
                fragmentSignupBinding.signupPassword.text.isEmpty()) {

                Toast.makeText(activity, "Please enter your credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fragmentSignupBinding.signupPassword.text.isEmpty() &&
                fragmentSignupBinding.signupConfirmPassword.text.isEmpty()) {

                Toast.makeText(activity, "Please enter your credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fragmentSignupBinding.signupUsername.text.isEmpty() &&
                fragmentSignupBinding.signupConfirmPassword.text.isEmpty()) {

                Toast.makeText(activity, "Please enter your credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fragmentSignupBinding.signupUsername.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fragmentSignupBinding.signupPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fragmentSignupBinding.signupConfirmPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please confirm your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            email = fragmentSignupBinding.signupUsername.text.toString()
            password = fragmentSignupBinding.signupPassword.text.toString()
            passConfirm = fragmentSignupBinding.signupConfirmPassword.text.toString()

            signupTask(email, password, passConfirm)
        }

        fragmentSignupBinding.loginButton.setOnClickListener { v ->
            (context as LoginActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, LoginFragment.newInstance())
                .commit()
        }

        return view
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