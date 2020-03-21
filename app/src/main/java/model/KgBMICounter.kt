package model

class KgBMICounter : BMICounter(MIN_WEIGHT, MAX_WEIGHT, MIN_HEIGHT, MAX_HEIGHT) {

    override fun calculateBMIInternal(weight: Float, height: Float) = weight * 10000f / (height * height)

    companion object {
        private const val MIN_WEIGHT = 20f
        private const val MAX_WEIGHT = 200f
        private const val MIN_HEIGHT = 50f
        private const val MAX_HEIGHT = 250f
    }

}
