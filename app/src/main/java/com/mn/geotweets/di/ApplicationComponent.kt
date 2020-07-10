package com.mn.geotweets.di

import android.content.Context
import com.mn.domain.NetworkHandler
import com.mn.domain.common.TokenManager
import com.mn.geotweets.common.NetworkHandlerImpl
import com.mn.geotweets.common.TokenManagerImpl
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }

    val authComponent: AuthComponent
    val mainComponent: MainComponent
}

@Module
interface ApplicationModule {
    @Binds
    fun networkHandler(networkHandlerImpl: NetworkHandlerImpl): NetworkHandler

    @Binds
    fun tokenManager(tokenManagerImpl: TokenManagerImpl): TokenManager
}
