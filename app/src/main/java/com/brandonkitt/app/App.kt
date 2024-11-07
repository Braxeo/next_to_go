package com.brandonkitt.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Basic Android Application with Hilt for DI.
 * Here we can include global setup of dependencies such as
 * Crashlytics and additional logging.
 */
@HiltAndroidApp
class App : Application()