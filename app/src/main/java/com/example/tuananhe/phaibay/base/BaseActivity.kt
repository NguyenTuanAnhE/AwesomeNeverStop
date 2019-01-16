package com.example.tuananhe.phaibay.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mNavigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavigator = Navigator(this)
        initBinding(getContentLayout())
        initView()
        initComponent()

    }

    abstract fun initBinding(id: Int)

    abstract fun initView()

    abstract fun initComponent()

    abstract fun getContentLayout(): Int

    protected fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = window.decorView
        }
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    protected fun getNavigator(): Navigator = mNavigator
}