package activites

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trello_clone.R
import com.projemanag.activities.BaseActivity

class my_profile : BaseActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = findViewById(R.id.toolbar_my_profile_activity)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_profile)
        setupActionBar()
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
}