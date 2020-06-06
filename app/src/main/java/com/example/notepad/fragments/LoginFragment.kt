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
import com.example.notepad.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private var email: String? = null
    private var password: String? = null
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater, container,false)
        val view = fragmentLoginBinding.root

        firebaseAuth = FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser!=null) {
            val intent: Intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("id", firebaseAuth.currentUser?.email)
            startActivity(intent)
        }

        fragmentLoginBinding.loginButton.setOnClickListener {

            if (fragmentLoginBinding.loginUsername.text.isEmpty() && fragmentLoginBinding.loginPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your credentials", Toast.LENGTH_SHORT).show()
                Log.d("LOGIN FRAGMENT", "Properly working")
                return@setOnClickListener
            }

            if (fragmentLoginBinding.loginUsername.text.isEmpty()) {
                Toast.makeText(activity, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fragmentLoginBinding.loginPassword.text.isEmpty()) {
                Toast.makeText(activity, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            email = fragmentLoginBinding.loginUsername.text.toString()
            password = fragmentLoginBinding.loginPassword.text.toString()

            Toast.makeText(activity, "Logging in...", Toast.LENGTH_SHORT).show()
            loginTask(email!!, password!!)
        }

        fragmentLoginBinding.createAccountButton.setOnClickListener {

            (context as LoginActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, SignupFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    private fun loginTask(email: String, pass: String) {

        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                val intent: Intent = Intent(activity, MainActivity::class.java)
                intent.putExtra("id", firebaseAuth.currentUser?.email)
                startActivity(intent)
            }

            else {
                Toast.makeText(activity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}