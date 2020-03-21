package model

import android.animation.ValueAnimator
import android.graphics.Color
import android.widget.TextView
import java.lang.Float.POSITIVE_INFINITY


abstract class BMICounter(
    private val MIN_WEIGHT: Float,
    private val MAX_WEIGHT: Float,
    private val MIN_HEIGHT: Float,
    private val MAX_HEIGHT: Float
) {

    fun calculateBMI(weight: Float, height: Float) = when {
        isValidWeight(weight) && isValidHeight(height) -> calculateBMIInternal(weight, height)
        else -> throw IllegalArgumentException("weight: $weight, height: $height")
    }

    fun isValidWeight(weight: Float) = weight in MIN_WEIGHT..MAX_WEIGHT

    fun isValidHeight(height: Float) = height in MIN_HEIGHT..MAX_HEIGHT

    abstract fun calculateBMIInternal(weight: Float, height: Float): Float

    companion object {
        private val kgBMICounter = KgBMICounter()
        private val lbBMICounter = LbBMICounter()

        fun get(bmiCalculatorType: BMICounterType) = when (bmiCalculatorType) {
            BMICounterType.METRIC -> kgBMICounter
            BMICounterType.IMPERIAL -> lbBMICounter
        }

        fun startCountAnimation(min: Float, max: Float, textView: TextView, bmi: Float): BMIStatus {
            val valueAnimator = ValueAnimator.ofFloat(min, max)
            valueAnimator.duration = 600
            valueAnimator.addUpdateListener {
                val animatedValue = it.animatedValue as Float
                println(animatedValue)
                textView.textSize = animatedValue

            }
            when (bmi) {
                in 30f..POSITIVE_INFINITY -> {
                    textView.setTextColor(Color.parseColor("#F5451F"))
                    valueAnimator.start()
                    return BMIStatus.OBESE
                }
                in 25f..30f -> {
                    textView.setTextColor(Color.parseColor("8B43B9"))
                    valueAnimator.start()
                    return BMIStatus.OVERWEIGHT
                }
                in 18f..25f -> {
                    textView.setTextColor(Color.parseColor("6DCB49"))
                    valueAnimator.start()
                    return BMIStatus.NORMAL

                }
                else -> {
                    textView.setTextColor(Color.parseColor("#F5451F"))
                    valueAnimator.start()
                    return BMIStatus.UNDERWEIGHT
                }
            }

        }
    }

}

