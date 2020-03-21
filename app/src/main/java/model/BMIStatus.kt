package model

import com.example.myapplication.R


enum class BMIStatus(
    val descriptionId: Int
) {

    OBESE(R.string.obese),
    OVERWEIGHT(R.string.overweight),
    NORMAL(R.string.normal),
    UNDERWEIGHT(R.string.underweight),


}
