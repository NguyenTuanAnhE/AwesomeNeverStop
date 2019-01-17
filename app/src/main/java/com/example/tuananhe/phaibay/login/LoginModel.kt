package com.example.tuananhe.phaibay.login

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Intent
import com.example.tuananhe.phaibay.base.Navigator
import com.example.tuananhe.phaibay.floatingbubble.BubbleService
import com.example.tuananhe.phaibay.signup.SignUpActivity


class LoginModel(application: Application, var mNavigator: Navigator) :
    AndroidViewModel(application) {

    fun forgotPassword() {
        val intent = Intent(mNavigator.getContext(), BubbleService::class.java)
        mNavigator.startService(intent)
    }

}
