package com.aaandroiddev.cryptowatcher.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.util.ArrayMap
import com.aaandroiddev.cryptowatcher.di.ViewModelSubComponent
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ABIDI Ahmed on 22/04/2018.
 */

@Singleton
open class ViewModelFactory : ViewModelProvider.Factory {

    private lateinit var creators: ArrayMap<Class<*>, Callable<out ViewModel>>

    @Inject
    fun ViewModelFactory(viewModelSubComponent: ViewModelSubComponent) {
        creators = ArrayMap()

        // View models cannot be injected directly because they won't be bound to the owner's
        // view model scope.

       // creators.put(HomeViewModel::class.java, viewModelSubComponent.homeViewModel())

    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Callable<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class $modelClass")
        }
        try {
            return creator.call() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}