package activites

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.trello_clone.R
import com.projemanag.activities.BaseActivity
import models.User

class my_profile : BaseActivity() {
    companion object{
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }
    private lateinit var profile_image: ImageView
    private lateinit var et_name: EditText
    private lateinit var et_email: EditText
    private lateinit var et_mobile: EditText
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = findViewById(R.id.toolbar_my_profile_activity)
        et_name = findViewById(R.id.et_name)
        et_email = findViewById(R.id.et_email)
        et_mobile = findViewById(R.id.et_mobile)
        profile_image = findViewById(R.id.iv_profile_user_image)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_profile)
        setupActionBar()
        FireStoreClass().loadUserData(this)

        profile_image.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }


    }


         private fun setupActionBar(){
             setSupportActionBar(findViewById(R.id.toolbar_my_profile_activity))
            val actionBar = supportActionBar
             if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
                 actionBar.title = resources.getString(R.string.my_profile)

            }
             toolbar.setNavigationOnClickListener { onBackPressed() }


    }
    fun setUserDataInUI(user : User){
        Glide
            .with(this@my_profile)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(findViewById(R.id.iv_profile_user_image))

        et_name.setText(user.name)
        et_email.setText(user.email)
        if(user.mobile != 0L){
            et_mobile.setText(user.mobile.toString())
        }
    }
}