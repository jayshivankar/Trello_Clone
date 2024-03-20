package activites

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.example.trello_clone.R
import com.projemanag.activities.BaseActivity

class IntroActivity : BaseActivity() {
    private lateinit var btn_sign_up_intro: Button
    private lateinit var btn_sign_in_intro: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        btn_sign_up_intro = findViewById(R.id.btn_sign_up_intro)
        btn_sign_in_intro = findViewById(R.id.btn_sign_in_intro)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        btn_sign_up_intro.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        btn_sign_in_intro.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}