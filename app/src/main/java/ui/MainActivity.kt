package ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*
import model.BMICounter
import model.BMICounterType

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    private var bmiCounterType = BMICounterType.METRIC
    private var bmiCounter = BMICounter.get(bmiCounterType)

    private var bmi = 0f
    private var isCorrectWeight = false
    private var isCorrectHeight = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        weight_txt.doOnTextChanged { _: CharSequence?, _: Int, _: Int, _: Int ->
            weightWatcher()
        }

        height_txt.doOnTextChanged { _: CharSequence?, _: Int, _: Int, _: Int ->
            heightWatcher()
        }

        bmi_switch.setOnClickListener {
            switchClickListener()
        }

        btn_count.setOnClickListener {
            calculateBMI()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_about -> {
                val about = Intent(applicationContext, About::class.java)
                startActivity(about)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun weightWatcher() {
        validateWeight(weight_txt.text.toString())
    }

    private fun heightWatcher() {
        validateHeight(height_txt.text.toString())
    }

    private fun switchClickListener() {
        val bmiCalculatorType = if (bmi_switch.isChecked) {
            BMICounterType.IMPERIAL
        } else {
            BMICounterType.METRIC
        }
        setCalculatorType(bmiCalculatorType)
        validateWeight(weight_txt.text.toString())
        validateHeight(height_txt.text.toString())
    }

    private fun calculateBMI() {
        val weightString = weight_txt.text.toString()
        val heightString = height_txt.text.toString()
        bmi = 0f
        if (isCorrectHeight && isCorrectWeight) {
            val weight = weightString.toFloat()
            val height = heightString.toFloat()
            bmi = bmiCounter.calculateBMI(weight, height)
        }
        setTextViews(bmi)
    }

    private fun validateWeight(weightString: String) {
        if (weightString != "") {
            val weight = weightString.toFloat()
            isCorrectWeight = bmiCounter.isValidWeight(weight)
            if (isCorrectWeight) {
                weight_txt.error = null
            } else {
                weight_txt.error = getString(R.string.invalid_weight)
            }
        } else {
            isCorrectWeight = false
        }
    }

    private fun validateHeight(heightString: String) {
        if (heightString != "") {
            val height = heightString.toFloat()
            isCorrectHeight = bmiCounter.isValidHeight(height)
            if (isCorrectHeight) {
                height_txt.error = null
            } else {
                height_txt.error = getString(R.string.invalid_height)
            }
        } else {
            isCorrectHeight = false
        }
    }

    private fun setCalculatorType(bmiCalculatorType: BMICounterType) {
        this.bmiCounterType = bmiCalculatorType
        this.bmiCounter = BMICounter.get(bmiCalculatorType)
        weight_txt.setText(bmiCalculatorType.weightDescriptionId)
        height_txt.setText(bmiCalculatorType.heightDescriptionId)
    }

    private fun setTextViews(bmi: Float) {
        if (bmi == 0f) {
            bmi_view.text = ""
            bmi_result.text = ""
            bmi_result_view.text = ""
        } else {
            bmi_view.setText(R.string.bmi_value)
            bmi_result.text = getFormattedBMI(bmi)
            bmi_result_view.setText(BMICounter.startCountAnimation(0f,bmi,bmi_result_view,bmi).descriptionId)
        }
    }

    private fun getFormattedBMI(bmi: Float): String {
        return String.format(resources.configuration.locales[0], "%.2f", bmi)
    }
}
