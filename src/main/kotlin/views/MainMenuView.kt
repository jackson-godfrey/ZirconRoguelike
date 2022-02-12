package com.example.views

import brain.Brain
import main.GameConfig
import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView
import views.brainView.BrainView

class MainMenuView(
    private val grid: TileGrid
) : BaseView(grid, GameConfig.THEME){


    init {
        val msg = "[Game Title]"

        val header = Components.textBox(contentWidth = msg.length)
            .addHeader(msg)
            .addNewLine()
            .withAlignmentWithin(screen, ComponentAlignment.CENTER)
            .withDecorations(ComponentDecorations.box())
            .build()

        val startButton = Components.button()
            .withText("Placeholder START button")
            .withAlignmentAround(header, ComponentAlignment.BOTTOM_CENTER)
            .withDecorations(ComponentDecorations.box())
            .build()

            .apply {
                //onActivated { replaceWith(PlayView(grid)) }
            }

        val brainButton = Components.button()
            .withText("Take me to the brain!")
            .withAlignmentAround(startButton, ComponentAlignment.BOTTOM_CENTER)
            .withDecorations(ComponentDecorations.box())
            .build()

            .apply {
                onActivated { replaceWith(BrainView(grid, Brain())) }
            }

        screen.addComponents(header, startButton, brainButton)
    }
}