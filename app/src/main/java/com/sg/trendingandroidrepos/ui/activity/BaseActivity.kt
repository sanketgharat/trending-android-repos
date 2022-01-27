package com.sg.trendingandroidrepos.ui.activity

import android.content.Context
import android.os.Bundle
import android.os.Message
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getIntentData()
    abstract fun initViews()
    abstract fun initObservers()
    abstract fun loadData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showToast(message: String){
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }
}