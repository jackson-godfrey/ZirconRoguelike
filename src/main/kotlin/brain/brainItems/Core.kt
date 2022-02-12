package brain.brainItems

import main.CardinalDirection

class Core (
    inGameName: String,
    renderer: BrainItemRenderer,
    outletDirections: Set<CardinalDirection>
) : BrainItem (
    inGameName,
    renderer,
    outletDirections,
    isNaturalPowerSource = true
)

{
}