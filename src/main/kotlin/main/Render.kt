package main

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.TileGraphics

class Render {

    companion object {

//        fun TileGraphics.drawTileBackTransparent(glyph: Char,
//                                                 pos: Position,
//                                                 foreground: TileColor) {
//            draw(TileBuilder.newBuilder()
//                .withCharacter(glyph)
//                .withForegroundColor(foreground)
//                //.withBackgroundColor(TileColor.transparent())
//                .build(),
//
//                pos)


        fun TileGraphics.drawTile(glyph: Char,
                                  pos: Position,
                                  foreground: TileColor,
                                  background: TileColor) {
            draw(TileBuilder.newBuilder()
                .withCharacter(glyph)
                .withForegroundColor(foreground)
                .withBackgroundColor(background)
                .build(),

                pos)
        }
    }
}