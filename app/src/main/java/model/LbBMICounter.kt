package model

class LbBMICounter : BMICounter(MIN_WEIGHT, MAX_WEIGHT, MIN_HEIGHT, MAX_HEIGHT) {

    override fun calculateBMIInternal(weight: Float, height: Float) = weight * 703f / (height * height)

    companion object {
        private const val MIN_WEIGHT = 20f
        private const val MAX_WEIGHT = 440f
        private const val MIN_HEIGHT = 20f
        private const val MAX_HEIGHT = 100f
    }

}
