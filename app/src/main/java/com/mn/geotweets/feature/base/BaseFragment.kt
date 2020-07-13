package com.mn.geotweets.feature.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mn.geotweets.GeoTweetsApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val application by lazy {
        context?.applicationContext as GeoTweetsApplication
    }

    abstract fun layoutId(): Int
    abstract fun viewCreated(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated(view)
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(id: Int) {
        val message = getString(id)
        showToast(message)
    }

    fun openUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    fun <T, U, V> withViewModel(viewModel: BaseViewModel<T, U, V>) = ViewModelLambda(viewModel)

    fun <T, U, V> ViewModelLambda<T, U, V>.state(block: (T) -> Unit): ViewModelLambda<T, U, V> {
        return this.apply { state = block }
    }

    fun <T, U, V> ViewModelLambda<T, U, V>.event(block: (U) -> Unit): ViewModelLambda<T, U, V> {
        return this.apply { event = block }
    }

    fun <T, U, V> ViewModelLambda<T, U, V>.error(block: (V) -> Unit): ViewModelLambda<T, U, V> {
        return this.apply { error = block }
    }

    fun <T, U, V> ViewModelLambda<T, U, V>.loading(block: (Boolean) -> Unit): ViewModelLambda<T, U, V> {
        return this.apply { loading = block }
    }

    inner class ViewModelLambda<T, U, V>(var viewModel: BaseViewModel<T, U, V>) {
        var state: (T) -> Unit = {}
            set(value) {
                field = value
                viewModel.state().observe(this@BaseFragment, Observer { value(it) })
            }
        var event: (U) -> Unit = {}
            set(value) {
                field = value
                viewModel.event().observe(this@BaseFragment, Observer { event ->
                    event.getContentIfNotHandled()?.let {
                        value(it)
                    }
                })
            }
        var error: (V) -> Unit = {}
            set(value) {
                field = value
                viewModel.error().observe(this@BaseFragment, Observer { event ->
                    event.getContentIfNotHandled()?.let {
                        value(it)
                    }
                })
            }

        var loading: (Boolean) -> Unit = {}
            set(value) {
                field = value
                viewModel.loading().observe(this@BaseFragment, Observer { show ->
                    value(show)
                })
            }
    }

    protected inline fun <reified T> viewModel(): T where T : ViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
    }

}