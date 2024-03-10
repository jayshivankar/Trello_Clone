
package activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.trello_clone.R

class SignInActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        toolbar = findViewById(R.id.toolbar_sign_in_activity)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupActionBar()
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
}