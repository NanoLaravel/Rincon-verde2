package com.example.rincon_verde2

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RinconVerde2Application : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            // Solo inicializar si se ha configurado un ID real
            val appId = getString(R.string.facebook_app_id)
            if (appId != "123456789") { // Reemplaza por tu ID por defecto o check dummy
                FacebookSdk.sdkInitialize(applicationContext)
                AppEventsLogger.activateApp(this)
            }
        } catch (e: Exception) {
            // Evitar que la app se caiga si falla la config de Facebook
            android.util.Log.e("Application", "Facebook SDK initialization failed", e)
        }
    }
}
