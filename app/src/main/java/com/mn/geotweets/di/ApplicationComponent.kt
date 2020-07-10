package com.mn.geotweets.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mn.domain.NetworkHandler
import com.mn.domain.common.TokenManager
import com.mn.geotweets.common.NetworkHandlerImpl
import com.mn.geotweets.common.TokenManagerImpl
import com.mn.geotweets.feature.auth.AuthFragment
import com.mn.geotweets.feature.auth.AuthViewModel
import com.mn.geotweets.feature.base.ViewModelFactory
import dagger.*
import dagger.multibindings.IntoMap
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
