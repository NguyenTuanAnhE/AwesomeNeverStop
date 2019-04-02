package com.example.tuananhe.phaibay.home

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import com.example.tuananhe.phaibay.base.Navigator

class HomeViewModelFactory(var application: Application, var navigator: Navigator) :
    ViewModelProviders.DefaultFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeModel(application, navigator) as T
    }
}