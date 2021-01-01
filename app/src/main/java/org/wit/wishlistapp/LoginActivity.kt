package org.wit.wishlistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_register.setOnClickListener{

        }

        btn_login.setOnClickListener{
            when {
                TextUtils.isEmpty(login_username.text.toString().trim() {it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter your username",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(login_password.text.toString().trim() {it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "please enter your password",
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {
                    val email : String = login_username.text.toString().trim() {it <= ' '}
                    val password: String = login_password.text.toString().trim() {it <= ' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@LoginActivity,
                                    "You are registered successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("user_email", email)
                                startActivity(intent)
                                finish()
                            }else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                }

            }

        }
    }
}