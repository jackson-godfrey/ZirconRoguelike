package views.brainView

import brain.Brain
import brain.brainItems.BrainItemLibrary
import main.GameConfig
import main.GameConfig.BASE_WINDOW_WIDTH
import main.GameConfig.BRAIN_HEIGHT
import main.GameConfig.BRAIN_ITEM_HEIGHT
import main.GameConfig.BRAIN_ITEM_WIDTH
import main.GameConfig.BRAIN_WIDTH
import main.GameConfig.DEBUG_EXTRA_WIDTH
import main.GameConfig.WINDOW_HEIGHT
import main.GameConfig.isDebugMode
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView
import org.hexworks.zircon.internal.fragment.impl.VerticalScrollableList

class BrainView(
    grid: TileGrid,
    private val brain: Brain
) : BaseView(grid, GameConfig.THEME){

    init{
        val itemGridPanel = Components.panel()
            .withPosition(0, 0)
            .withPreferredSize(BRAIN_ITEM_WIDTH * BRAIN_WIDTH, BRAIN_ITEM_HEIGHT * BRAIN_HEIGHT)
            .build()

        screen.addComponents(itemGridPanel)

         val focusedItemBox = FocusedItemBoxBuilder(brain=brain)
             .withAlignmentAround(itemGridPanel, ComponentAlignment.TOP_RIGHT)
             .build()
        screen.addComponent(focusedItemBox)

        itemGridPanel.apply {
            for(x in 0 until BRAIN_WIDTH)
                for(y in 0 until BRAIN_HEIGHT)
                    addComponent(
                        BrainBoxBuilder(x, y, brain)
                            .withPosition(x*BRAIN_ITEM_WIDTH, y*BRAIN_ITEM_HEIGHT)
                            .build()
                            .apply{
                                    onActivated { _ ->
                                        focusedItemBox.focusedItemPosition = Position.create(x, y)
                                    }
//                                    onDeactivated {
//                                        focusedItemBox.focusedItemPosition = FocusedItemBox.NO_FOCUSED_POSITION
//                                    }
                            }
                    )
        }

        if(isDebugMode()) {
            val debugPanel = Components.panel()
                .withPosition(BASE_WINDOW_WIDTH, 0)
                .withPreferredSize(DEBUG_EXTRA_WIDTH, WINDOW_HEIGHT)

                .build()

            debugPanel.addFragment(
                VerticalScrollableList(
                    size = Size.create(DEBUG_EXTRA_WIDTH, WINDOW_HEIGHT-10),
                    position = Position.create(0, 0),
                    BrainItemLibrary.allBrainItems,
                    onItemActivated = { item, _ ->
                        Brain.Debug.itemToPlace = item
                    },
                    renderItem = {
                        it.inGameName
                    }
                )
            )

            screen.addComponent(debugPanel)
        }

    }
}