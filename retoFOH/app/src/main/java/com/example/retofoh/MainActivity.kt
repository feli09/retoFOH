package com.example.retofoh

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.retofoh.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController by lazy { findNavController(R.id.mainNavHostFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun setUpView() {
        val navigationController =
            Navigation.findNavController(this, R.id.mainNavHostFragment)
        navigationController.navigate(R.id.homeFragmentNav, intent.extras)
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (navController.currentDestination?.id == R.id.homeFragmentNav) {
            navController.popBackStack(R.id.homeFragmentNav,false )
            true
        } else {
            navController.navigateUp()
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.homeFragmentNav) {
            setResult(Activity.RESULT_CANCELED)
            finish()
        } else super.onBackPressed()
    }
}
