package com.star.mvvm.mapdiary

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel

abstract class BaseActivity <VM : ViewModel, VB : ViewDataBinding> (@LayoutRes private val layoutId: Int) : AppCompatActivity() {

        protected abstract val mViewModel: VM
        protected lateinit var mViewBinding: VB

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            //화면 회전 고정 (세로)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            mViewBinding = DataBindingUtil.setContentView(this, layoutId)

            observe()
            initView()
        }

        /**
         * Default toolbar
         */
        open fun useToolbarDefault(title: Int) {
            var tb = findViewById<Toolbar>(R.id.tb_toolbar_custom).apply {
                this.title = getString(title)
                setNavigationIcon(R.drawable.ic_arrow_back_gs_100)
            }

            setSupportActionBar(tb)
            tb.setNavigationOnClickListener { onBackPressed() }
        }

        /**
         * 표출되어 있는 키보드를 숨김
         */
        protected fun hideKeyboard() {
            getSystemService(Context.INPUT_METHOD_SERVICE).let {
                if(it is InputMethodManager) it.hideSoftInputFromWindow(currentFocus?.windowToken,0)
            }
        }

        fun AlertDialog.showCheck() {
            if(!this.isShowing) {
                this.show()
            }
        }


        fun AlertDialog.hideCheck() {
            if(this.isShowing) {
                this.dismiss()
            }
        }

        /**
         * 중복 클릭 방지
         */
        fun singleClickFunc(onClick: () -> Any): View.OnClickListener = object : OnSingleClickListener() {
            override fun onSingleClick(v: View?) { onClick() }
        }

        /**
         * View 이벤트 및 최초 실행되야 하는 작업 처리
         */
        open fun initView() {}

        /**
         * ViewModel LiveData 구현
         */
        open fun observe() {}
    }
}