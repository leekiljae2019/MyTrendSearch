package com.afterwork.mytrendsearch.view

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.afterwork.mytrendsearch.R
import com.afterwork.mytrendsearch.databinding.ActivityDetailwebBinding
import com.afterwork.mytrendsearch.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detailweb.*
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class DetailWebActivity: BaseActivity<ActivityDetailwebBinding>(){

    companion object{
        val TAG = "DetailWebActivity"
        val URL = "URL"
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_detailweb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vmDetailWeb = getViewModel{parametersOf(intent.getStringExtra(URL))}
        binding.lifecycleOwner = this

        Log.d(TAG, "onCreate(url: ${intent.getStringExtra(URL)})")
    }

    override fun onDestroy() {
        super.onDestroy()

        web_detail.destroy()
    }
}