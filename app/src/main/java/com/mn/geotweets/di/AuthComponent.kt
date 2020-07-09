package com.mn.geotweets.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mn.data.auth.AuthApi
import com.mn.data.auth.AuthRepositoryImpl
import com.mn.domain.usecase.auth.AuthRepository
import com.mn.geotweets.BuildConfig
import com.mn.geotweets.feature.auth.AuthFragment
import com.mn.geotweets.feature.auth.AuthViewModel
import com.mn.geotweets.feature.base.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap
import oauth.signpost.http.HttpParameters
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor

@Subcomponent(modules = [AuthRepositoryModule::class, AuthModule::class, ViewModelModule::class])
interface AuthComponent {
    fun inject(fragment: AuthFragment)
}

@Module
interface AuthRepositoryModule {
    @Binds
    fun authRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}

@Module
class AuthModule {
    @Provides
    fun oauthConsumer(): OkHttpOAuthConsumer {
        val consumer = OkHttpOAuthConsumer(BuildConfig.API_KEY, BuildConfig.API_SECRET)
        HttpParameters().apply {
            put("oauth_callback", "geotweets://")
        }.let {
            consumer.setAdditionalParameters(it)
        }
        return consumer
    }

    @Provides
    fun okHttpClient(consumer: OkHttpOAuthConsumer): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(SigningInterceptor(consumer))
            .build()
    }

    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun authApi(retrofit: Retrofit) = retrofit.create(AuthApi::class.java)
}

@Module
interface ViewModelModule {

    @Binds
    fun viewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun authViewModel(viewModel: AuthViewModel): ViewModel
}