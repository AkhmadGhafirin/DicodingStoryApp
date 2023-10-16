package com.cascer.dicodingstoryapp.ui.authentication

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cascer.dicodingstoryapp.data.model.RegisterRequest
import com.cascer.dicodingstoryapp.databinding.ActivityRegisterBinding
import com.cascer.dicodingstoryapp.utils.gone
import com.cascer.dicodingstoryapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (name.isEmpty()) {
            binding.etName.error = "Name wajib diisi"
        } else if (email.isEmpty()) {
            binding.etEmail.error = "Email wajib diisi"
        } else if (password.isEmpty()) {
            binding.etPassword.error = "Password wajib diisi"
        } else {
            viewModel.register(RegisterRequest(name, email, password))
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            isRegisterSuccess.observe(this@RegisterActivity) {
                Toast.makeText(
                    this@RegisterActivity, "Berhasil mendaftarkan akun baru", Toast.LENGTH_SHORT
                ).show()
                finish()
            }

            isLoading.observe(this@RegisterActivity) {
                if (it) {
                    binding.progressbar.visible()
                    binding.btnRegister.isEnabled = false
                } else {
                    binding.btnRegister.isEnabled = true
                    binding.progressbar.gone()
                }
            }

            errorMsg.observe(this@RegisterActivity) {
                Toast.makeText(this@RegisterActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}