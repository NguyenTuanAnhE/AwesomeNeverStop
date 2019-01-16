package com.example.tuananhe.phaibay.signup

import android.databinding.DataBindingUtil
import com.example.tuananhe.phaibay.R
import com.example.tuananhe.phaibay.base.BaseActivity
import com.example.tuananhe.phaibay.databinding.ActivitySignupBinding

class SignUpActivity : BaseActivity() {

    private lateinit var mActivitySignupBinding: ActivitySignupBinding

    override fun initView() {
    }

    override fun initComponent() {
    }

    override fun getContentLayout(): Int = R.layout.activity_signup

    override fun initBinding(id: Int) {
        mActivitySignupBinding = DataBindingUtil.setContentView(this, id)
    }

}