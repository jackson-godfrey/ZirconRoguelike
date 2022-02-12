package brain.brainItems

import main.CardinalDirection

// Relics represent items which have passive effects
abstract class Relic(
    inGameName: String,
    renderer: BrainItemRenderer,
    outletDirections: Set<CardinalDirection> = mutableSetOf(),
    passiveAbilityHolder: PassiveAbilityHolder = object:PassiveAbilityHolder {},
    isNaturalPowerSource: Boolean = false,
) : BrainItem(
    inGameName,
    renderer,
    outletDirections,
    isNaturalPowerSource),

    PassiveAbilityHolder by passiveAbilityHolder
{

}