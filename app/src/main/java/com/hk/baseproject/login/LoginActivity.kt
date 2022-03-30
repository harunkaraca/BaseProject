package com.hk.baseproject.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.hk.baseproject.R
import com.hk.baseproject.databinding.ActivityLoginBinding
import com.hk.baseproject.di.MyApplication
import com.hk.baseproject.main.MainActivity
import com.hk.baseproject.util.setupSnackbar
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent.loginComponent().create().inject(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        view.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_LONG)
        initListeners()
        observeViewModel()
    }
    fun initListeners(){
        binding.buttonLogin.setOnClickListener {
            viewModel.login()
        }
    }

    fun observeViewModel(){
        viewModel.isDataLoadingError.observe(this,{isDataLoadingError->
            isDataLoadingError?.let {
                val activityIntent= Intent(applicationContext, MainActivity::class.java)
                startActivity(activityIntent)
                finish() }
        })
        viewModel.dataLoading.observe(this, {isLoading->
            isLoading?.let {
                binding.loadingView.visibility=if(it) View.VISIBLE else View.GONE
            }
        })
    }
}