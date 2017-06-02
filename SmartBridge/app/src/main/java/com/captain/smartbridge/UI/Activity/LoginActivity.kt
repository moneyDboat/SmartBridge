package com.captain.smartbridge.UI.Activity

import butterknife.ButterKnife
import butterknife.OnClick
import com.captain.smartbridge.R
import com.captain.smartbridge.model.Account

/**
 * Created by fish on 17-4-24.
 */

class LoginActivity : AbsActivity() {

    private var baseApplication: BaseApplication? = null

    @OnClick(R.id.login_buttom) internal fun login() {
        if (postLogin()) {
            readyGoThenKill(MainActivity::class.java)
        } else {
            //提示用户名密码不正确
        }

    }

    override fun setSelfContentView() {
        setContentView(R.layout.activity_login)
    }

    override fun prepareDatas() {
        baseApplication = application as BaseApplication
    }

    override fun initViews() {
        ButterKnife.bind(this)
    }

    private fun postLogin(): Boolean {
        
        val account = Account()
        account.category = 0
        baseApplication!!.account = account
        return true
    }
}
