package com.brandonkitt.core.data.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Basic Network Change BCReceiver to handle network changes
 */
class NetworkChangeReceiver @Inject constructor() :
    BroadcastReceiver() {
    private lateinit var _lifecycleScope: CoroutineScope
    fun setLifecycleScope(lifecycleScope: CoroutineScope) {
        _lifecycleScope = lifecycleScope
    }
    // This is the SharedFlow that subscribers will listen to.
    private val _networkStatusFlow = MutableSharedFlow<Boolean>(replay = 1)
    val networkStatusFlow: SharedFlow<Boolean> = _networkStatusFlow.asSharedFlow()
    override fun onReceive(context: Context?, intent: Intent?) {
        // Get ConnectivityManager Service
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Get nullable activeNetwork
        val activeNetwork = connectivityManager.activeNetworkInfo
        // Check for null, connected or connecting status
        val isConnected = activeNetwork?.isConnectedOrConnecting == true

        if (::_lifecycleScope.isInitialized) {
            // Emit the network status to the SharedFlow
            _lifecycleScope.launch {
                _networkStatusFlow.emit(isConnected)
            }
        } else {
            // Enforce lifecycleScope be set.
            throw Exception("LifecycleScope for NetworkChangeReceiver was not set before onReceive was called")
        }
    }
}