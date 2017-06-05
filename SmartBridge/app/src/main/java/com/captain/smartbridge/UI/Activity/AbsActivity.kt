package com.captain.smartbridge.UI.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.captain.smartbridge.Common.CommonUtils
import com.captain.smartbridge.R

/**
 * Created by fish on 17-4-24.
 */

abstract class AbsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSelfContentView()
        prepareDatas()
        initViews()
    }

    protected abstract fun setSelfContentView()

    protected abstract fun prepareDatas()

    protected abstract fun initViews()

    protected fun readyGo(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }

    protected fun readyGo(clazz: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, clazz)
        if (null != bundle) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    protected fun readyGoThenKill(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
        finish()
    }

    protected fun readyGoThenKill(clazz: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, clazz)
        if (null != bundle) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        finish()
    }

    protected fun readyGoForResult(clazz: Class<*>, requestCode: Int) {
        val intent = Intent(this, clazz)
        startActivityForResult(intent, requestCode)
    }

    protected fun readyGoForResult(clazz: Class<*>, requestCode: Int, bundle: Bundle?) {
        val intent = Intent(this, clazz)
        if (null != bundle) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    protected fun showToast(msg: String?) {
        if (null != msg && !CommonUtils.isEmpty(msg)) {
            Snackbar.make(window.decorView, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    protected fun showNetWorkError() {
        showToast(resources.getString(R.string.network_error_tips))
    }
    //
    //    protected String getInnerErrorInfo(RetrofitError error) {
    //        ErrorRes res = (ErrorRes) error.getBodyAs(ErrorRes.class);
    //        if (res != null) {
    //            return res.getReason();
    //        } else {
    //            return getString(R.string.common_error_msg);
    //        }
    //    }

    //    /**
    //     * toggle show loading
    //     *
    //     * @param toggle
    //     */
    //    protected void toggleShowLoading(boolean toggle, String msg) {
    //        if (null == mVaryViewHelperController) {
    //            throw new IllegalArgumentException("You must return a right target view for loading");
    //        }
    //
    //        if (toggle) {
    //            mVaryViewHelperController.showLoading(msg);
    //        } else {
    //            mVaryViewHelperController.restore();
    //        }
    //    }
    //
    //    /**
    //     * toggle show empty
    //     *
    //     * @param toggle
    //     */
    //    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
    //        if (null == mVaryViewHelperController) {
    //            throw new IllegalArgumentException("You must return a right target view for loading");
    //        }
    //
    //        if (toggle) {
    //            mVaryViewHelperController.showEmpty(msg, onClickListener);
    //        } else {
    //            mVaryViewHelperController.restore();
    //        }
    //    }
    //
    //    /**
    //     * toggle show error
    //     *
    //     * @param toggle
    //     */
    //    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
    //        if (null == mVaryViewHelperController) {
    //            throw new IllegalArgumentException("You must return a right target view for loading");
    //        }
    //
    //        if (toggle) {
    //            mVaryViewHelperController.showError(msg, onClickListener);
    //        } else {
    //            mVaryViewHelperController.restore();
    //        }
    //    }
    //
    //    /**
    //     * toggle show network error
    //     *
    //     * @param toggle
    //     */
    //    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
    //        if (null == mVaryViewHelperController) {
    //            throw new IllegalArgumentException("You must return a right target view for loading");
    //        }
    //
    //        if (toggle) {
    //            mVaryViewHelperController.showNetworkError(onClickListener);
    //        } else {
    //            mVaryViewHelperController.restore();
    //        }
    //    }

    protected fun filterException(e: Exception?): Boolean {
        if (e != null) {
            e.printStackTrace()
            showToast(e.message)
            return false
        } else {
            return true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
