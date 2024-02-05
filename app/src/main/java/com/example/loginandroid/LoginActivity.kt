package com.example.loginandroid

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(){
    private var binding : ActivityLoginBinding? = null
    private var user : String = ""
    private var pass : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnlogin.setOnClickListener {
            user = binding!!.etUsername.text.toString()
            pass = binding!!.etPassword.text.toString()

            when {
                user == "" -> {
                    binding!!.etUsername.error = "Username Tidak Boleh Kosong"
                }
                pass == "" -> {
                    binding!!.etPassword.error = "Pasword Tidak Boleh kosong"
                }
                else -> {
                    binding!!.loading.visibility = view.VISIBLE
                }
            }
        }
    }
}