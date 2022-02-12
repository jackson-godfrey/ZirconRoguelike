package brain.brainItems

import brain.Circuitry
import main.CardinalDirection

abstract class BrainItem(
    val inGameName: String,
    renderer: BrainItemRenderer,
    outletDirections: Set<CardinalDirection> = setOf(),
    isNaturalPowerSource: Boolean = false

) : Circuitry(outletDirections.toMutableSet(), isNaturalPowerSource = isNaturalPowerSource),
        BrainItemRenderer by renderer
{

}
