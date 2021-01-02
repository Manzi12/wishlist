package org.wit.wishlistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_sign_up.setOnClickListener{
            when {
                TextUtils.isEmpty(sign_username.text.toString().trim() {it <= ' '}) -> {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Please enter your username",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(sign_password.text.toString().trim() {it <= ' '}) -> {
                    Toast.makeText(
                        this@SignUpActivity,
                        "please enter your password",
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {
                    val email : String = sign_username.text.toString().trim() {it <= ' '}
                    val password: String = sign_password.text.toString().trim() {it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@SignUpActivity,
                                    "You are registered successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("user_email", email)
                                startActivity(intent)
                                finish()
                            }else {
                                Toast.makeText(
                                    this@SignUpActivity,
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

