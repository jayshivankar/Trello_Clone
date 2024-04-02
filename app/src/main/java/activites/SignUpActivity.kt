package activites

import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import com.example.trello_clone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.projemanag.activities.BaseActivity
import models.User




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

    fun userRegisteredSuccess() {
        // Hide the progress dialog
        hideProgressDialog()
        // Show the success message and finish the sign up activity
        showErrorSnackBar("You are registered successfully. Your user ID is ${FirebaseAuth.getInstance().currentUser!!.uid}")
        FirebaseAuth.getInstance().signOut()
        finish()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }



        private fun registerUser() {
        val name: String = findViewById<EditText>(R.id.et_name).text.toString().trim()
        val email: String = findViewById<EditText>(R.id.et_email).text.toString().trim()
        val password: String = findViewById<EditText>(R.id.et_password).text.toString().trim()

        if (validateForm(name, email, password)) {
            // Show progress dialog
            showProgressDialog(getString(R.string.please_wait))

            // Create user with email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    // Hide progress dialog
                    hideProgressDialog()

                    // Handle registration result
                    if (task.isSuccessful) {
                        // Registration successful
                        val firebaseUser: FirebaseUser  = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        val user = User(firebaseUser.uid, name, registeredEmail)
                        FireStoreClass().registerUser(this, user)

                        showErrorSnackBar("You are registered successfully. Your user ID is ${firebaseUser.uid}")

                            // Sign out the user and finish the activity
                            FirebaseAuth.getInstance().signOut()
                            finish()

                    } else {
                        // Registration failed, show error message
                        showErrorSnackBar(task.exception?.message ?: "Registration failed.")

                    }
                }
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            name.isEmpty() -> {
                showErrorSnackBar("Please enter a name")
                false
            }
            email.isEmpty() -> {
                showErrorSnackBar("Please enter an email")
                false
            }
            password.isEmpty() -> {
                showErrorSnackBar("Please enter a password")
                false
            }
            else -> true
        }
    }
}
