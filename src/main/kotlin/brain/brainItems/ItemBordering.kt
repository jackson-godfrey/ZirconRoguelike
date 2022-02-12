package brain.brainItems

import main.CardinalDirection
import main.DiagonalDirection
import main.Direction

data class ItemBordering(
    private val cornerGlyphs: CornerGlyphSet = CornerGlyphSet.SINGLE_LINE,
    private val nonCircuitryCardinalGlyphs: CardinalGlyphSet = CardinalGlyphSet.EMPTY,
    private val circuitryCardinalGlyphs: CardinalGlyphSet = CardinalGlyphSet.STANDARD_DOUBLE_LINE,
    val cornersAreCircuitry: Boolean = false //Should the corners be illuminated when connected to power
)
{
    fun getGlyph(dir: Direction, circuitry:Boolean) : Char{
        if(dir is CardinalDirection && circuitry){
            return circuitryCardinalGlyphs.getGlyph(dir)
        } else if(dir is CardinalDirection) {
            return nonCircuitryCardinalGlyphs.getGlyph(dir)
        }else if(dir is DiagonalDirection){
            return cornerGlyphs.getGlyph(dir)
        }

        return 'X' //Should never happen
    }

    companion object {
        val DEFAULT = ItemBordering()
        val EMPTY = ItemBordering(
            cornerGlyphs = CornerGlyphSet.EMPTY,
            nonCircuitryCardinalGlyphs = CardinalGlyphSet.EMPTY,
        )
    }
}