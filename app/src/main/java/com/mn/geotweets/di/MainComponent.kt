package com.mn.geotweets.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mn.data.tweets.TweetsApi
import com.mn.data.tweets.TweetsRepositoryImpl
import com.mn.domain.common.TokenManager
import com.mn.domain.usecase.tweets.TweetsRepository
import com.mn.geotweets.BuildConfig
import com.mn.geotweets.feature.base.ViewModelFactory
import com.mn.geotweets.feature.map.TweetMapFragment
import com.mn.geotweets.feature.map.TweetMapViewModel
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

@Subcomponent(modules = [MainModule::class, MainRepositoryModule::class, MainViewModelModule::class])
interface MainComponent {
    fun inject(fragment: TweetMapFragment)
}

@Module
interface MainRepositoryModule {
    @Binds
    fun tweetsRepository(tweetsRepository: TweetsRepositoryImpl): TweetsRepository
}

@Module
class MainModule {
    @Provides
    fun oauthConsumer(tokenManager: TokenManager): OkHttpOAuthConsumer {
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
    fun tweetsApi(retrofit: Retrofit) = retrofit.create(TweetsApi::class.java)
}

@Module
interface MainViewModelModule {
    @Binds
    fun viewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TweetMapViewModel::class)
    fun tweetMapViewModel(viewModel: TweetMapViewModel): ViewModel
}