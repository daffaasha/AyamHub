package com.bangkit.ayamhub.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.databinding.ActivityRegisterBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener { validateInput() }
    }

    private fun signUp(
        name: String,
        username: String,
        password: String,
        email: String,
        phoneNumber: String
    ) {
        viewModel.signUp(name, username, password, email, phoneNumber).observe(this){ result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Log.e( "RegisterUMKMActivity","OnFailure: ${result.error}")
                        Reusable.showToast(this, "Oops gagal mendaftar")
                    }
                }
            }
        }
    }

    private fun validateInput() {
        with (binding) {
            val name = edRegisterName.text.toString()
//            val username = etUsername.text.toString()
            val password = edRegisterPassword.text.toString()
            val email = edRegisterEmail.text.toString()
            val phone = edRegisterPhone.text.toString()

            when {
                name.isEmpty() -> {
                    edRegisterName.error = "Tolong isi namanya ya"
                }
//                username.isEmpty() -> {
//                    etUsername.error = "Tolong isi usernamenya"
//                }
                email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    edRegisterEmail.error = "Tolong isi email dengan benar ya"
                }
                phone.isEmpty() -> {
                    edRegisterPhone.error = "Tolong isi nomor handphone dengan benar ya"
                }
                password.isEmpty() || password.length < 8 -> {
                    edRegisterPassword.error = "Tolong isi passwordnya dengan benar ya"
                }
                else -> {
                    signUp(name, name, password, email, phone)
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

}