package com.cascer.dicodingstoryapp.ui.authentication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cascer.dicodingstoryapp.data.model.LoginRequest
import com.cascer.dicodingstoryapp.databinding.ActivityLoginBinding
import com.cascer.dicodingstoryapp.ui.story.list.StoryActivity
import com.cascer.dicodingstoryapp.utils.gone
import com.cascer.dicodingstoryapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (email.isEmpty()) {
            binding.etEmail.error = "Email wajib diisi"
        } else if (password.isEmpty()) {
            binding.etPassword.error = "Password wajib diisi"
        } else {
            viewModel.login(LoginRequest(email, password))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkIsLogin()
    }

    private fun setupViewModel() {
        with(viewModel) {
            isLogin.observe(this@LoginActivity) {
                if (it) startActivity(
                    Intent(
                        this@LoginActivity, StoryActivity::class.java
                    )
                ).also { finish() }
            }

            isLoginSuccess.observe(this@LoginActivity) {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        StoryActivity::class.java
                    )
                ).also { finish() }
            }

            isLoading.observe(this@LoginActivity) {
                if (it) {
                    binding.progressbar.visible()
                    binding.btnLogin.isEnabled = false
                } else {
                    binding.btnLogin.isEnabled = true
                    binding.progressbar.gone()
                }
            }

            errorMsg.observe(this@LoginActivity) {
                Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}