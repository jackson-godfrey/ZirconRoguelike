package brain

import main.CardinalDirection

abstract class Circuitry (
    private val outletDirections: MutableSet<CardinalDirection> = mutableSetOf(),
    private val rotatable: Boolean = true,
    val isNaturalPowerSource: Boolean = false, //Non-natural sources can exist with certain brain configurations
) {

    //Default rotation is zero degrees
    private var rotation = CardinalDirection.NORTH
    fun rotateCircuitryClockwise(){
        if(rotatable) { rotation++ }
    }

    fun getRotatedOutletDirections(): List<CardinalDirection> = outletDirections.toList().map { it + rotation }
    fun addRandomOutletDirection() {

    }


}