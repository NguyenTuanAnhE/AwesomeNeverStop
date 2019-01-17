package com.example.tuananhe.phaibay.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import com.example.tuananhe.phaibay.R
import com.example.tuananhe.phaibay.base.BaseActivity
import com.example.tuananhe.phaibay.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(), View.OnFocusChangeListener {

    private val fireBaseAuth = FirebaseAuth.getInstance()
    private lateinit var mMainActivityBinding: ActivityLoginBinding
    private lateinit var mLoginModel: LoginModel

    override fun initBinding(id: Int) {
        mMainActivityBinding = DataBindingUtil.setContentView(this, id)
        mLoginModel =
                ViewModelProviders.of(this, LoginViewModelFactory(application, getNavigator()))
                    .get(LoginModel::class.java)
        mMainActivityBinding.viewModel = mLoginModel
    }

    override fun initView() {
        edit_text_email.onFocusChangeListener = this
        edit_text_phone.onFocusChangeListener = this
        edit_text_password.onFocusChangeListener = this
    }

    override fun initComponent() {
        checkBubblePermission()
    }

    override fun getContentLayout(): Int = R.layout.activity_login

    override fun onResume() {
        super.onResume()
        hideKeyboard()
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            v?.background = getDrawable(R.drawable.border_edit_text_red)
        } else {
            v?.background = getDrawable(R.drawable.border_edit_text_gray)
        }
    }

    private fun checkBubblePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, 1)
        }
    }

}
