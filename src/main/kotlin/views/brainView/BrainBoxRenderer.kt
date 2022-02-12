package views.brainView

import brain.Brain
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TileGraphics

class BrainBoxRenderer (val brain: Brain) : ComponentRenderer<BrainBox> {


    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<BrainBox>) {
        val brainBox = context.component

        brain.callRender(brainBox.brainGridX, brainBox.brainGridY, tileGraphics)
    }
}