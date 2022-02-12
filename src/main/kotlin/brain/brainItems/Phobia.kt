package brain.brainItems

import main.CardinalDirection

class Phobia(
    inGameName: String,
    renderer: BrainItemRenderer,
    outletDirections: Set<CardinalDirection> = mutableSetOf(),
    passiveAbilityHolder: PassiveAbilityHolder = object:PassiveAbilityHolder {},
    isNaturalPowerSource: Boolean = false,
) : Relic(
    inGameName,
    renderer,
    outletDirections,
    passiveAbilityHolder,
    isNaturalPowerSource)
{

}