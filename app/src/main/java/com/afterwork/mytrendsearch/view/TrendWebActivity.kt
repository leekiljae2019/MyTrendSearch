package com.afterwork.mytrendsearch.view

import android.os.Bundle
import android.util.Log
import com.afterwork.mytrendsearch.R
import com.afterwork.mytrendsearch.databinding.ActivityTrendwebBinding
import com.afterwork.mytrendsearch.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_trendweb.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class TrendWebActivity: BaseActivity<ActivityTrendwebBinding>(){

    companion object{
        val TAG = "TrendWebActivity"
        val URL = "URL"
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_trendweb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vmTrendWeb = getViewModel{parametersOf(intent.getStringExtra(URL))}
        binding.lifecycleOwner = this

        Log.d(TAG, "onCreate(url: ${intent.getStringExtra(URL)})")
    }

    override fun onDestroy() {
        super.onDestroy()

        web_trend.destroy()
    }
}