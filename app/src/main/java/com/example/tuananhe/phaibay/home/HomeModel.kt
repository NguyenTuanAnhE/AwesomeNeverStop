package com.example.tuananhe.phaibay.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Intent
import com.example.tuananhe.phaibay.base.Navigator
import com.example.tuananhe.phaibay.floatingbubble.BubbleService

class HomeModel(application: Application, var navigator: Navigator) :
    AndroidViewModel(application) {

    fun startBubble() {
        val intent = Intent(navigator.getContext(), BubbleService::class.java)
        navigator.startService(intent)
    }

}
