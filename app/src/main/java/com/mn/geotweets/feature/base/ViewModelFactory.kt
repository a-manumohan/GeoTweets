package com.mn.geotweets.feature.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.ClassCastException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val viewModelProviderMap: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val provider = viewModelProviderMap[modelClass]
            ?: viewModelProviderMap.asIterable()
                .firstOrNull {
                    modelClass.isAssignableFrom(it.key)
                }?.value
            ?: throw IllegalArgumentException(
                "Unknown model class $modelClass." +
                        " Check module definitions"
            )

        return try {
            provider.get() as T
        } catch (e: ClassCastException) {
            throw IllegalArgumentException("Provider did not return expected ViewModel $e")
        }
    }
}