package com.example.trello_clone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class SignUpActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        toolbar = findViewById(R.id.toolbar_sign_up_activity)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}