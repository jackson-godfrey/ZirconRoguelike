package brain.brainItems

import main.CardinalDirection
import main.DiagonalDirection
import main.Direction
import org.hexworks.zircon.api.graphics.Symbols

abstract class GlyphQuad<T: Direction> (
    protected val glyph1: Char,
    protected val glyph2: Char,
    protected val glyph3: Char,
    protected val glyph4: Char,
){

    //fun getGlyph(d: DiagonalDirection) = listOf(glyph1, glyph2, glyph3, glyph4)[d.ordinal]
    //fun getGlyph(d: CardinalDirection) = listOf(glyph1, glyph2, glyph3, glyph4)[d.ordinal]
    abstract fun getGlyph(d: T) : Char
}

class CornerGlyphSet(
    topLeft: Char,
    topRight: Char,
    bottomRight: Char,
    bottomLeft: Char,
): GlyphQuad<DiagonalDirection>(topLeft, topRight, bottomRight, bottomLeft)
{
    override fun getGlyph(d: DiagonalDirection) = listOf(glyph1, glyph2, glyph3, glyph4)[d.ordinal]

    companion object {
        val EMPTY = CornerGlyphSet(' ', ' ', ' ', ' ')

        val SINGLE_LINE = CornerGlyphSet(
            Symbols.SINGLE_LINE_TOP_LEFT_CORNER,
            Symbols.SINGLE_LINE_TOP_RIGHT_CORNER,
            Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER,
            Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER
        )

        val DOUBLE_LINE = CornerGlyphSet(
            Symbols.DOUBLE_LINE_TOP_LEFT_CORNER,
            Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER,
            Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER,
            Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER
        )
    }
}

class CardinalGlyphSet(
    top: Char,
    right: Char,
    bottom: Char,
    left: Char
) : GlyphQuad<CardinalDirection>(top, right, bottom, left)
{
    override fun getGlyph(d: CardinalDirection) = listOf(glyph1, glyph2, glyph3, glyph4)[d.ordinal]

    companion object {
        val EMPTY = CardinalGlyphSet(' ', ' ', ' ', ' ')

        val STANDARD_DOUBLE_LINE = CardinalGlyphSet(
            Symbols.DOUBLE_LINE_VERTICAL,
            Symbols.DOUBLE_LINE_HORIZONTAL,
            Symbols.DOUBLE_LINE_VERTICAL,
            Symbols.DOUBLE_LINE_HORIZONTAL,
        )

        val ROTATED_DOUBLE_LINE = CardinalGlyphSet(
            Symbols.DOUBLE_LINE_HORIZONTAL,
            Symbols.DOUBLE_LINE_VERTICAL,
            Symbols.DOUBLE_LINE_HORIZONTAL,
            Symbols.DOUBLE_LINE_VERTICAL,
        )

        val JUNCTION_DOUBLE_LINE = CardinalGlyphSet(
            Symbols.DOUBLE_LINE_T_UP,
            Symbols.DOUBLE_LINE_T_RIGHT,
            Symbols.DOUBLE_LINE_T_DOWN,
            Symbols.DOUBLE_LINE_T_LEFT,
        )
    }
}