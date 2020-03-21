package model

import com.example.myapplication.R

enum class BMICounterType(
    val weightDescriptionId: Int,
    val heightDescriptionId: Int
) {

    IMPERIAL(R.string.lb_weight_value, R.string.in_height_value),
    METRIC(R.string.kg_weight_value, R.string.cm_height_value)

}
