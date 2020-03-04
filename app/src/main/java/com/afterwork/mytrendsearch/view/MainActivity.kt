package com.afterwork.mytrendsearch.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import com.afterwork.mytrendsearch.R
import com.afterwork.mytrendsearch.databinding.ActivityMainBinding
import com.afterwork.mytrendsearch.paging.TrendPagingAdapter
import com.afterwork.mytrendsearch.view.base.BaseActivity

import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object{
        val TAG = "MainActivity"
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vmMain = getViewModel()
        binding.lifecycleOwner = this

        list.adapter = TrendPagingAdapter({
            var intent = Intent(this@MainActivity, DetailWebActivity::class.java)
            intent.putExtra(DetailWebActivity.URL, it)
            startActivity(intent)
        })

        binding.vmMain?.trendSearchUrl?.observe(this, Observer {
            Log.d(TAG, "trendSearchUrl: ${it}")

            if(it.isNullOrEmpty() == false) {
                var intent = Intent(this@MainActivity, TrendWebActivity::class.java)
                intent.putExtra(TrendWebActivity.URL, it)
                startActivity(intent)
            }
        })

        binding.vmMain?.load()?.observe(this, Observer {
            Log.d(TAG, "Observe event: ${it.size}")
            (list.adapter as TrendPagingAdapter).submitList(it)
            binding.vmMain?.loaded()
        })

    }
}
