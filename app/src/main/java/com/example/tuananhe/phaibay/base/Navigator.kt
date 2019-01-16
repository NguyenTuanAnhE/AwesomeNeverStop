package com.example.tuananhe.phaibay.base

import android.app.Activity
import android.content.Context
import android.content.Intent

class Navigator(private var mActivity: Activity) {

    fun getContext(): Context = mActivity

    fun startActivity(intent: Intent) {
        mActivity.startActivity(intent)
    }

}
