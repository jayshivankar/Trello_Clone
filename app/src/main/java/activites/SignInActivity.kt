
package activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.trello_clone.R
import com.google.firebase.auth.FirebaseAuth
import com.projemanag.activities.BaseActivity
import models.User

class SignInActivity : BaseActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var auth : FirebaseAuth
    private lateinit var btn_signin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        btn_signin = findViewById(R.id.btn_sign_in)
        auth = FirebaseAuth.getInstance()
        toolbar = findViewById(R.id.toolbar_sign_in_activity)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        btn_signin.setOnClickListener {
            signInUser()
        }
        setupActionBar()
    }

    fun signInSuccess(user : User){
        hideProgressDialog()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun setupActionBar(){
        setSupportActionBar(findViewById(R.id.toolbar_sign_in_activity))

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun signInUser(){
        val email: String = findViewById<EditText>(R.id.et_email_signin).text.toString().trim { it <= ' ' }
        val password: String = findViewById<EditText>(R.id.et_password_signin).text.toString().trim { it <= ' ' }
        if(validateForm(email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        Log.d("Sign In", "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, MainActivity::class.java))

                        finish()
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
    private fun validateForm(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                showErrorSnackBar("Please enter an email")
                false
            }

            password.isEmpty() -> {
                showErrorSnackBar("Please enter a password")
                false
            }

            else -> {
                true
            }
        }

    }


}
