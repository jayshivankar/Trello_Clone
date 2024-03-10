package activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import com.example.trello_clone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.projemanag.activities.BaseActivity

class SignUpActivity : BaseActivity() {
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

    private fun registerUser(){
        val name : String = findViewById<EditText>(R.id.et_name).text.toString().trim{it <= ' '}
        val email : String = findViewById<EditText>(R.id.et_email).text.toString().trim{it <= ' '}
        val password : String = findViewById<EditText>(R.id.et_password).text.toString().trim{it <= ' '}

        if(validateForm(name,email,password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task ->

                }
        }
    }


    private fun validateForm(name : String ,email : String,password:String): Boolean{
        return when{
            name.isEmpty() ->{
                showErrorSnackBar("Please enter a name")
                false
            }
            email.isEmpty() ->{
                showErrorSnackBar("Please enter an email")
                false
            }
            password.isEmpty() ->{
                showErrorSnackBar("Please enter a password")
                false
            }
            else ->{
                true
            }
        }

    }
}