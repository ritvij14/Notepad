package com.example.notepad.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notepad.R
import com.example.notepad.fragments.LoginFragment

class LoginActivity : AppCompatActivity() {

    var loginFragment: LoginFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout, loginFragment).commit()

    }
}