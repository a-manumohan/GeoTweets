package com.mn.geotweets.di

import dagger.Module
import dagger.Subcomponent

@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {
}

@Module
class AuthModule