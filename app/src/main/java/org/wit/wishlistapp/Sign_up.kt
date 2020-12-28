package org.wit.wishlistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class Sign_up : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)
        auth = FirebaseAuth.getInstance()

        btn_sign_up.setOnClickListener{
            signUpUser()
        }
    }
}