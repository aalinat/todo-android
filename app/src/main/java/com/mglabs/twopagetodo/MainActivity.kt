package com.mglabs.twopagetodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.mglabs.twopagetodo.ui.navigation.AppNavHost
import com.mglabs.twopagetodo.ui.theme.TwopagetodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwopagetodoTheme {
                AppNavHost(navController = rememberNavController())
            }
        }
    }
}

