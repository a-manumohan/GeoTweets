package com.mn.geotweets.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mn.domain.NetworkHandler
import com.mn.geotweets.common.NetworkHandlerImpl
import com.mn.geotweets.feature.auth.AuthFragment
import com.mn.geotweets.feature.auth.AuthViewModel
import com.mn.geotweets.feature.base.ViewModelFactory
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [ViewModelModule::class, ApplicationModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }

    val authComponent: AuthComponent

    fun inject(fragment: AuthFragment)
}

@Module
interface ApplicationModule {
    @Binds
    fun networkHandler(networkHandlerImpl: NetworkHandlerImpl): NetworkHandler

    @Binds
    fun viewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun authViewModel(viewModel: AuthViewModel): ViewModel
}
