package brain.brainItems

import main.CardinalDirection

class Memory(
    inGameName: String,
    renderer:BrainItemRenderer,
    outletDirections: Set<CardinalDirection> = mutableSetOf(),
    passiveAbilityHolder: PassiveAbilityHolder = object:PassiveAbilityHolder {},

) : Relic(
    inGameName,
    renderer,
    outletDirections,
    passiveAbilityHolder)
{

}