package com.stdev.newsdemo.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.stdev.newsdemo.databinding.ActivityMainBinding
import com.stdev.newsdemo.presentation.adapter.NewsAdapter
import com.stdev.newsdemo.presentation.adapter.SavedNewsAdapter
import com.stdev.newsdemo.presentation.viewmodel.NewsViewModel
import com.stdev.newsdemo.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: NewsViewModelFactory
    lateinit var viewModel : NewsViewModel
    @Inject
    lateinit var newsAdapter: NewsAdapter
    @Inject
    lateinit var savedNewsAdapter: SavedNewsAdapter
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        //val navController = navHostFragment.navController
        //binding.bottomNavigationView.setupWithNavController(navController)
        viewModel = ViewModelProvider(this,factory)[NewsViewModel::class.java]
    }
    //to support navigate up
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

}