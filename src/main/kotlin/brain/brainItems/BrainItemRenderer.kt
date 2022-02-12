package brain.brainItems

import main.CardinalDirection
import main.DiagonalDirection
import main.GameConfig.BRAIN_ITEM_HEIGHT
import main.GameConfig.BRAIN_ITEM_WIDTH
import main.Render.Companion.drawTile
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.TileGraphics

interface BrainItemRenderer {

    companion object {
        private val CENTER = Position.create(2, 2)

        private val UNPOWERED_COLOR = TileColor.create(150, 150, 150)
        private val POWERED_COLOR = TileColor.create(120, 220, 230)

        fun BrainItem.render(graphics: TileGraphics, powered: Boolean) {
            if(graphics.width != BRAIN_ITEM_WIDTH || graphics.height != BRAIN_ITEM_HEIGHT)
                throw Exception("BrainItemRenderer: Graphics object with incorrect size passed to render()")
            renderCentralGlyph(graphics, powered)
            renderCorners(graphics, powered)
            renderCircuitry(graphics, powered)
        }

        private fun BrainItemRenderer.renderCentralGlyph(graphics: TileGraphics, powered: Boolean){
            val fgColor: TileColor
            val bgColor: TileColor

            if(colorGlyphInstead){
                fgColor = highlight
                bgColor = TileColor.transparent()
            } else {
                fgColor = TileColor.transparent() //Black
                bgColor = if(glyphIsCircuitry){
                    if(powered) POWERED_COLOR
                    else UNPOWERED_COLOR
                } else
                    TileColor.transparent()
            }

            graphics.drawTile(glyph, CENTER, fgColor, bgColor)
        }

        private fun BrainItemRenderer.renderCorners(graphics: TileGraphics, powered: Boolean){

            val foreground: TileColor = if(bordering.cornersAreCircuitry && powered){
                POWERED_COLOR
            } else if(bordering.cornersAreCircuitry){
                UNPOWERED_COLOR
            } else {
                highlight //If corners aren't part of the circuitry, they are generally colored
            }
            val background: TileColor = TileColor.transparent()

            DiagonalDirection.values().forEach {
                graphics.drawTile(bordering.getGlyph(it, powered),
                    CENTER + it.toRelativePosition(), foreground, background)
            }
        }

        private fun BrainItem.renderCircuitry(  graphics: TileGraphics,
                                                powered: Boolean){
            val fgColor = if(powered) POWERED_COLOR else UNPOWERED_COLOR

            CardinalDirection.values().forEach {
                val circuitry: Boolean = getRotatedOutletDirections().contains(it)

                if(circuitry){
                    val circuitryChar: Char = when(it){
                        CardinalDirection.NORTH -> Symbols.DOUBLE_LINE_VERTICAL
                        CardinalDirection.SOUTH -> Symbols.DOUBLE_LINE_VERTICAL
                        CardinalDirection.WEST -> Symbols.DOUBLE_LINE_HORIZONTAL
                        CardinalDirection.EAST -> Symbols.DOUBLE_LINE_HORIZONTAL
                    }

                    graphics.draw(
                        TileBuilder.newBuilder()
                            .withCharacter(bordering.getGlyph(it, circuitry=true))
                            .withBackgroundColor(TileColor.transparent()) //GameConfig.brainStyleSet.backgroundColor)
                            .withForegroundColor(fgColor)
                            //.withModifiers(Modifiers.blink())
                            .build(),
                        CENTER + it.toRelativePosition()
                    )

                    graphics.draw(
                        TileBuilder.newBuilder()
                            .withCharacter(circuitryChar)
                            .withBackgroundColor(TileColor.transparent())
                            .withForegroundColor(fgColor)
                            //.withModifiers(Modifiers.blink())
                            .build(),
                        CENTER + it.toRelativePosition()*2
                    )
                }
                else{
                    graphics.draw(
                        TileBuilder.newBuilder()
                            .withCharacter(bordering.getGlyph(it, false))
                            .withBackgroundColor(TileColor.transparent())
                            .withForegroundColor(fgColor)
                            //.withModifiers(Modifiers.blink())
                            .build(),
                        CENTER + it.toRelativePosition()
                    )
                }
            }
        }

        private operator fun Position.times(i: Int) = Position.create(this.x*i, this.y*i)
    }

    val glyph: Char

    //Usually, the glyph will be a black foreground within a colored background. Should we color the glyph instead
    //and leave the background uncolored?:
    val colorGlyphInstead: Boolean
        get() = false

    val bordering: ItemBordering
        get() = ItemBordering.DEFAULT

    //Color that gives this relic it's flavor. Usually the color of the corners
    val highlight: TileColor
        get() = TileColor.create(220, 220, 220) //TODO(remove this getter)

    //'Will the central glyph be illuminated if the relic is connected to power'
    val glyphIsCircuitry: Boolean
        get() = true

}