package com.app.movieapp.baseclass

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.app.movieapp.R
import com.app.movieapp.databinding.DialogProgressbarBinding
import com.app.movieapp.model.ResponseData
import com.app.movieapp.utility.FAILURE
import com.app.movieapp.utility.Utils
import com.app.movieapp.interfaces.AppCompactImplMethod

/**
 * Created by Rahul Sadhu.
 */
abstract class BaseActivity : AppCompatActivity(), AppCompactImplMethod {
    lateinit var baseViewModel: BaseViewModel
    lateinit var viewModel: ViewModel
    private lateinit var binding: ViewDataBinding
    private lateinit var progressDialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    protected fun setView(layoutResId: Int) {

        try {
            binding = DataBindingUtil.setContentView(this, layoutResId)
            initVariable()
            loadData()
        } catch (e: Exception) {
            e.printStackTrace()
            Utils.showToast(this@BaseActivity, e.localizedMessage)
        }
    }


    // set ViewModel when BaseViewModel use
    inline fun <reified T : BaseViewModel> AppCompatActivity.getViewModel(): BaseViewModel {
        baseViewModel = ViewModelProviders.of(this)[T::class.java]
        setObserve()
        return baseViewModel as T
    }

    // set viewModel when BaseViewModel not use
    inline fun <reified T : ViewModel> AppCompatActivity.setViewModel(): ViewModel {
        viewModel = ViewModelProviders.of(this)[T::class.java]
        return viewModel
    }

    /*  fun setActionBar(toolbar: Toolbar, title: String?, isBack: Boolean) {
          action_bar.txt_title.text = title
          if (!isBack) {
              action_bar.img_back.visibility = View.INVISIBLE
          }
          action_bar.img_back.setOnClickListener { onBackPressed() }
      }*/


    fun setObserve() {
        baseViewModel.loading.observe(this, Observer { loading ->
            if (loading) showProgressDialog() else hideProgressDialog()
        })

        baseViewModel.apiResponse.observe(this, Observer {
            hideProgressDialog()
            if (it.success == FAILURE) {
                apiResponseError(it)
            } else {
                apiResponse(it)

            }

        })

        baseViewModel.apiError.observe(this, Observer { error ->
            baseViewModel.setLoading(false)
            apiError(error)
            if (!TextUtils.isEmpty(error)) {
                Utils.showToast(applicationContext, error)
                /* if (error == SESSION_EXPIRE_MSG) {
                     SharedPrefsManager.clearPrefs()
                     val intent = Intent(this, LoginActivity::class.java)
                     intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                     startNewActivity(intent)
                 }*/
            }

        })

    }

    open fun apiResponseError(it: ResponseData<*>?) {
        if (!TextUtils.isEmpty(it?.message)) {
            Utils.showToast(applicationContext, it?.message.toString())
        }
    }

    open fun apiError(error: String) {
        // when error call this method use in Activity
    }

    open fun apiResponse(response: ResponseData<*>) {
        // when response call this method use in Activity
    }

    protected fun getBinding(): ViewDataBinding {
        return binding
    }

    fun startNewActivity(intent: Intent) {
        //  Utils.hideKeyboard(BaseActivity.this);
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


    override fun onDestroy() {
        super.onDestroy()
        // remove observer for BaseViewModel
        if (::baseViewModel.isInitialized && baseViewModel.apiResponse.hasObservers()) {
            baseViewModel.apiResponse.removeObserver(baseViewModel.responseDataObserver)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Utils.hideKeyboard(BaseActivity.this);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun showProgressDialog(isCancel: Boolean = false) {
        if (!::progressDialog.isInitialized) {
            progressDialog = Dialog(this, R.style.ProgressDialog)
            val dialogBinding: DialogProgressbarBinding =
                DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_progressbar, null, false)
            progressDialog.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(dialogBinding.root)
                val layoutParams = window?.attributes
                layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
                setCanceledOnTouchOutside(false)
                setCancelable(false)
                window?.attributes = layoutParams
                window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                /* setOnDismissListener {
                          baseViewModel.getNetworkManager().cancelCall()
                          hideProgressDialog()
                 }*/
            }


        }


        if (::progressDialog.isInitialized && !progressDialog.isShowing) {
            progressDialog.show()
        }

    }

    fun hideProgressDialog() {
        if (::progressDialog.isInitialized && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun disableAutoFill() {
        window.decorView.importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
    }
}