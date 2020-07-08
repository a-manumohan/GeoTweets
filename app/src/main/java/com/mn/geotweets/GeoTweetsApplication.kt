package com.mn.geotweets

import android.app.Application
import com.mn.geotweets.di.ApplicationComponent
import com.mn.geotweets.di.DaggerApplicationComponent

class GeoTweetsApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}