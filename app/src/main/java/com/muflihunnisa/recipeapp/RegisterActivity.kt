package com.muflihunnisa.recipeapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_forgot_pass.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    companion object{
        fun getLaunchService (from: Context) = Intent(from, RegisterActivity::class.java).apply{
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        ib_back_regis.setOnClickListener(this)
        tv_login_regis.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.ib_back_regis -> startActivity(LoginActivity.getLaunchService(this))
            R.id.tv_login_regis-> startActivity(LoginActivity.getLaunchService(this))
        }
    }
}