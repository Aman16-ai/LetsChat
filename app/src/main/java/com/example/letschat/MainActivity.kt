package com.example.letschat

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.letschat.ui.Authentication.viewmodel.LogoutViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val logoutViewModel : LogoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.friendsFragment))
        toolbar = findViewById(R.id.toolbar)
        toolbar.setupWithNavController(navController,appBarConfiguration)
        setSupportActionBar(toolbar)

        navController.addOnDestinationChangedListener {_,desitnation,_->
            if(desitnation.id == R.id.loginFragment || desitnation.id == R.id.registerUserFragment) {
                toolbar.visibility = View.GONE
                window.statusBarColor = Color.parseColor("#3F2B96")
            }
            else {
                toolbar.visibility = View.VISIBLE
                window.statusBarColor = Color.parseColor("#588157")
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbarmenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout_btn) {
            Toast.makeText(this,"logout",Toast.LENGTH_SHORT).show()
            logoutViewModel.logout()
            navController.navigate(R.id.loginFragment)
        }
        else if(item.itemId == R.id.search_item) {
            navController.navigate(R.id.search_item)
        }
        else if(item.itemId == R.id.profile_item) {
            navController.navigate(R.id.profile)
        }
        return true
    }
}