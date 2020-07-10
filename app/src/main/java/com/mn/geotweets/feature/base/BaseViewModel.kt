package com.mn.geotweets.feature.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mn.geotweets.common.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, Ev, Er> :
    ViewModel() {
    private val viewModelJob: Job = SupervisorJob()

    protected val viewModelScope = CoroutineScope(Dispatchers.Default + viewModelJob)
    protected val mainScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val stateLiveData = MutableLiveData<S>()
    private val eventLiveData = MutableLiveData<Event<Ev>>()
    private val errorLiveData = MutableLiveData<Event<Er>>()
    private val loadingLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        mainScope.cancel()
    }

    protected fun state(state: S) = mainScope.launch {
        stateLiveData.value = state
    }

    protected fun event(event: Ev) = mainScope.launch {
        eventLiveData.value = Event(event)
    }

    protected fun error(error: Er) = mainScope.launch {
        errorLiveData.value = Event(error)
    }

    protected fun loading(show: Boolean) = mainScope.launch {
        loadingLiveData.value = show
    }

    fun state(): LiveData<S> = stateLiveData

    fun event(): LiveData<Event<Ev>> = eventLiveData

    fun error(): LiveData<Event<Er>> = errorLiveData

    fun loading(): LiveData<Boolean> = loadingLiveData
}