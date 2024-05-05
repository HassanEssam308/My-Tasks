package com.example.mytasksapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mytasksapp.dataBase.DataBase
import com.example.mytasksapp.databinding.ActivityMainBinding
import com.example.mytasksapp.fragments.AllTasksFragment

class MainActivity : AppCompatActivity() {

  private  lateinit var  binding: ActivityMainBinding
   private  lateinit var appBarConfig:AppBarConfiguration
  private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // set content in view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        navController=findNavController(R.id.nav_host_fragment_content_main)

        //change title of App Bar
        setSupportActionBar(binding.MainAppBarToolBar)
        appBarConfig = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfig)

    }



    override fun onSupportNavigateUp(): Boolean {
         super.onSupportNavigateUp()
        return   navController.navigateUp(appBarConfig)

    }

}