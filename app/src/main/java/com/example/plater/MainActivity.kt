package com.example.plater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.plater.util.Constants
import com.example.plater.view.dashboard.UserDataStore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_recipe_list.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var userDataStore: UserDataStore
    private var testI: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController


        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> navController.navigate(R.id.mainFragment)
                R.id.ic_categories -> navController.navigate(R.id.recipeCategoriesFragment)
                R.id.ic_favourites -> navController.navigate(R.id.favouriteRecipeFragment)
            }
            true
        }
    }
}