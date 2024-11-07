package com.brandonkitt.app

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.brandonkitt.core.data.network.NetworkChangeReceiver
import com.brandonkitt.core.theme.NextToGoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Entry point for the Single-Activity Android application.
 */
@AndroidEntryPoint
class AppActivity : ComponentActivity() {
    // NetworkReceiver for publishing network state changes
    @Inject
    lateinit var networkReceiver: NetworkChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Apply core theme
            NextToGoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Hilt Navigation for down the line when more features might
                    // be included
                    MyAppNavHost(navController = rememberNavController())
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Ensure the lifecycleScope is set for the activity
        networkReceiver.setLifecycleScope(lifecycleScope = lifecycleScope)
        // Register Intent for networkReceiver to handle network state changes
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        // Unregister Intent for networkReceiver
        unregisterReceiver(networkReceiver)
    }
}