package com.example.notepad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.notepad.R
import com.example.notepad.activities.LoginActivity
import kotlinx.android.synthetic.main.fragment_signup.view.*

class SignupFragment : Fragment() {

    private lateinit var signupUsername: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signupConfirmPassword: EditText
    private lateinit var signupButton: Button
    private lateinit var loginFragmentBtn: Button
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var passConfirm: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val signupFragmentLayout: View = inflater.inflate(R.layout.fragment_signup, container, false)
        signupUsername = signupFragmentLayout.signup_username
        signupPassword = signupFragmentLayout.signup_password
        signupConfirmPassword = signupFragmentLayout.signup_confirm_password
        signupButton = signupFragmentLayout.signup_button
        loginFragmentBtn = signupFragmentLayout.login_button

        signupButton.setOnClickListener {

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
}