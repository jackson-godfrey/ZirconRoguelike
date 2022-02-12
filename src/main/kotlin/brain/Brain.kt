package brain

import brain.brainItems.BrainItem
import brain.brainItems.BrainItemLibrary
import brain.brainItems.BrainItemRenderer.Companion.render
import main.GameConfig.BRAIN_HEIGHT
import main.GameConfig.BRAIN_WIDTH
import main.loop
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.TileGraphics

class Brain {
    companion object Debug {
        var itemToPlace: BrainItem = BrainItemLibrary.EmptyBrainItem
        fun replaceWithDebugItem(brain: Brain, brainGridX: Int, brainGridY: Int){
            brain.brainGrid[brainGridX][brainGridY] = BrainItemLibrary.buildFromExistingBrainItem(itemToPlace)
            brain.calculatePoweredTilesFromScratch()
            println(brain.poweredByGrid)
        }
    }

    private val brainGrid = Array<Array<BrainItem>> (BRAIN_WIDTH) {_ ->
        Array(BRAIN_HEIGHT) { _ ->
            BrainItemLibrary.EmptyBrainItem
        }
    }

    private val poweredByGrid = Array<Array<MutableSet<Position>>> (BRAIN_WIDTH) {_ ->
        Array(BRAIN_HEIGHT) { _ ->
            mutableSetOf()
        }
    }

//    //TODO(Change sets of positions to sets of cores)
//    //Hashmap relating sources to colors,
//    private val sourceColors: HashMap<Set<Position>, TileColor> = HashMap()

    init {
        calculatePoweredTilesFromScratch()
    }

    private fun getBrainItemAt(x: Int, y: Int) : BrainItem {
        if(!inBounds(x, y))
            throw Exception("Attempted access of brainItem outside bounds of brain")
        return brainGrid[x][y] //Array indexing in Kotlin comes with error returning bound checks
    }

    fun getBrainItemAt(pos: Position) : BrainItem {
        return getBrainItemAt(pos.x, pos.y)
    }

    private fun calculatePoweredTilesFromScratch(){

        val powerSources: MutableSet<Position> = mutableSetOf()
        brainGrid.loop { x, y ->
            if (this.isNaturalPowerSource)
                powerSources.add(Position.create(x, y))
        }
        val checkedPowerSources: MutableSet<Position> = mutableSetOf()

        //Clear the poweredByGrid, as we are calculating from scratch
        poweredByGrid.forEach { row ->
            row.forEach {col ->
                col.removeAll{true}
            }
        }

        //Loop through each power source and recursively permeate power throughout connected circuit
        powerSources.forEach { pos ->
            if(checkedPowerSources.contains(pos)) return@forEach //May as well not double count things
            checkedPowerSources.add(pos)

            //We keep track of other sources on the same circuit; all connected items (inclusive of sources) will
            //receive power from each connected source
            val connectedSources = mutableSetOf(pos)
            val connectedItems = mutableSetOf(pos)

            //region Local emanatePowerFrom(pos: Position) function declaration
            fun emanatePowerFrom(pos: Position) {
                val item = getBrainItemAt(pos.x, pos.y)

                if (item.isNaturalPowerSource) {
                    connectedSources.add(pos)
                    checkedPowerSources.add(pos)
                }

                //Loop through each outlet in item to see where to try and permeate power
                item.getRotatedOutletDirections().forEach {
                    val newPos = pos + it.toRelativePosition()
                    if (inBounds(newPos)
                        && !connectedItems.contains(newPos) //Have we already been here
                        && getBrainItemAt(newPos.x, newPos.y).getRotatedOutletDirections()
                            .contains(it.oppositeDirection()) //Does the adjacent item have an inlet for our outlet
                    ) {
                        connectedItems.add(newPos)
                        emanatePowerFrom(newPos)
                    }
                }
            }
            //endregion

            emanatePowerFrom(pos)

            connectedItems.forEach { pos ->
                poweredByGrid[pos.x][pos.y].addAll(connectedSources)
            }
            println(connectedItems)
            println(connectedSources)
            println()
        }
    }

    fun rotateCircuitry(x: Int, y: Int){
        val item = getBrainItemAt(x, y)
        item.rotateCircuitryClockwise()
        calculatePoweredTilesFromScratch()
    }
    private fun inBounds(pos: Position) : Boolean {
        return inBounds(pos.x, pos.y)
    }

    private fun inBounds(x: Int, y: Int) : Boolean {
        return (x in 0 until BRAIN_WIDTH && y in 0 until BRAIN_HEIGHT)
    }

    //Function exists so other classes don't need to know as much
    fun callRender(x: Int, y: Int, graphics: TileGraphics) {
        val item = getBrainItemAt(x, y)
        val powered: Boolean = (poweredByGrid[x][y].size > 0)
        item.render(graphics, powered)
    }

}