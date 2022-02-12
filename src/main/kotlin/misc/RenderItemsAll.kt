package misc

import brain.brainItems.BrainItemLibrary
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView

fun main(args: Array<String>) {
    val grid = SwingApplications.startTileGrid(
        AppConfig.newBuilder()
        //.withDefaultTileset(GameConfig.TILESET)
        .withSize(20*5, 13*5)
        .build())
    AllItemsView(grid).dock()
}

private class AllItemsView(
    private val grid: TileGrid
) : BaseView(grid, ColorThemes.monokaiOrange()){


    init {
        val itemGrid = Array<Array<ItemBox>> (20) { row ->
            Array<ItemBox>(13) {col ->
                ItemBoxBuilder(BrainItemLibrary.allBrainItems.elementAtOrElse(row + col*20) { _ -> BrainItemLibrary.EmptyBrainItem })
                    .withPosition(row*5, col*5)
                    .build()
                    .apply { screen.addComponent(this@apply) }
            }
        }
//        val msg = "[Game Title]"
//
//        val header = Components.textBox(contentWidth = msg.length)
//            .addHeader(msg)
//            .addNewLine()
//            .withAlignmentWithin(screen, ComponentAlignment.CENTER)
//            .withDecorations(ComponentDecorations.box())
//            .build()
//
//        val startButton = Components.button()
//            .withText("Placeholder START button")
//            .withAlignmentAround(header, ComponentAlignment.BOTTOM_CENTER)
//            .withDecorations(ComponentDecorations.box())
//            .build()
//
//            .apply {
//                //onActivated { replaceWith(PlayView(grid)) }
//            }
//
//        val brainButton = Components.button()
//            .withText("Take me to the brain!")
//            .withAlignmentAround(startButton, ComponentAlignment.BOTTOM_CENTER)
//            .withDecorations(ComponentDecorations.box())
//            .build()
//
//            .apply {
//                onActivated { replaceWith(BrainView(grid, Brain())) }
//            }

    }
}