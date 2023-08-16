package com.app.srtravels

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.srtravels.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    public lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navGraph: NavGraph
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when {
                navController.currentDestination?.label.toString() == "LoginFragment" -> {
                    binding.navView.visibility = View.GONE
                    binding.topBar.toolbar.visibility = View.GONE
                }
                navController.currentDestination?.label.toString() == "RgisterFragment" -> {
                    binding.navView.visibility = View.GONE
                    binding.topBar.toolbar.visibility = View.GONE
                }
                navController.currentDestination?.label.toString() == "FragmentHome" -> {
                    //binding.navView.visibility = View.GONE
                    binding.topBar.toolbar.visibility = View.GONE
                }
                else -> {
                    binding.navView.visibility = View.VISIBLE
                    binding.topBar.toolbar.visibility = View.VISIBLE
                }

            }
        }

        showBottomNavigationBar()
        setNavigationGraph()
    }

    private fun showBottomNavigationBar() {
        //if(navHostFragment)
    }

    private fun setNavigationGraph() {
        navGraph.setStartDestination(R.id.loginFragment)
        navController.graph = navGraph
    }

    fun getCurrentFragment(): androidx.fragment.app.Fragment? {
        return supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main)
    }


}
