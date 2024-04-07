package activites

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.trello_clone.R
import com.google.common.io.Files.getFileExtension
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.projemanag.activities.BaseActivity
import models.User

class my_profile : BaseActivity() {
    companion object{
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }
    private var mSelectedImageFileUri: Uri? = null
    private var mProfileImageURL: String = ""

    private lateinit var profile_image: ImageView
    private lateinit var btn_update: Button
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
        btn_update = findViewById(R.id.btn_update)
        profile_image = findViewById(R.id.iv_profile_user_image)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_profile)
        setupActionBar()
        FireStoreClass().loadUserData(this)

        profile_image.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                showImageChooser()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }
        btn_update.setOnClickListener {
            if (mSelectedImageFileUri != null) {
                uploadUserImage()
            } else {
                showProgressDialog(resources.getString(R.string.please_wait))
                updateUserProfileData()
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageChooser()
            } else {
                Toast.makeText(
                    this,
                    "Oops you denied the permission for storage.You can change it in settings",
                    Toast.LENGTH_LONG
                ).show( )
            }
        }
    }
    private fun showImageChooser(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK
            && requestCode == PICK_IMAGE_REQUEST_CODE
            && data!!.data != null){
            mSelectedImageFileUri = data.data
            try {
                Glide
                    .with(this@my_profile)
                    .load(mSelectedImageFileUri)
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_place_holder)
                    .into(profile_image)
            }catch (e: Exception){
                e.printStackTrace()
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
     private fun uploadUserImage() {
         showProgressDialog(resources.getString(R.string.please_wait))
         if (mSelectedImageFileUri != null) {
             val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                 "USER_IMAGE" + System.currentTimeMillis() + "." + getFileExtension(
                     mSelectedImageFileUri
                 )
             )

             sRef.putFile(mSelectedImageFileUri!!).addOnSuccessListener { taskSnapshot ->
                 Log.i(
                     "Firebase Image URL",
                     taskSnapshot.metadata!!.reference!!.downloadUrl.toString()

                 )
                 taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                     Log.e("Downloadable Image URL", uri.toString())
                     mProfileImageURL = uri.toString()

                 }
             }.addOnFailureListener{
                 exception->
                 Toast.makeText(this@my_profile, exception.message, Toast.LENGTH_LONG).show()
                 hideProgressDialog()
             }
         }
     }

    private fun getFileExtension(uri : Uri?):String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri!!))
    }
}