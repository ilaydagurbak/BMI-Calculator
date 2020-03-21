package ui


import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_about.*


class About : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        startWalking.setOnClickListener {
            val bgImage = imageView.background as AnimationDrawable
            bgImage.start()
        }

        stopWalking.setOnClickListener {
            val bgImage = imageView.background as AnimationDrawable
            bgImage.stop()
        }
    }
}
