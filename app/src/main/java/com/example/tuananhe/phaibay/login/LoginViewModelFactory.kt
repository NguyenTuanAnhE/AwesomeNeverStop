package com.example.tuananhe.phaibay.login

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.tuananhe.phaibay.base.Navigator

class LoginViewModelFactory(var mApplication: Application, var mNavigator: Navigator) :
    ViewModelProvider.AndroidViewModelFactory(mApplication) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginModel(mApplication, mNavigator) as T
    }

}